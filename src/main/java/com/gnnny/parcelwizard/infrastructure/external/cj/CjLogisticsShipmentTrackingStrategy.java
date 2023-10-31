package com.gnnny.parcelwizard.infrastructure.external.cj;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnnny.parcelwizard.application.shipment.ShipmentTrackingStrategy;
import com.gnnny.parcelwizard.domain.shipment.*;
import com.gnnny.parcelwizard.infrastructure.external.cj.CjApiResponse.ScanInfoOutput;
import com.gnnny.parcelwizard.infrastructure.external.cj.CjApiResponse.WblNoOutput;
import com.gnnny.parcelwizard.infrastructure.external.common.NonExistentTrackingNumberException;
import com.gnnny.parcelwizard.shared.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class CjLogisticsShipmentTrackingStrategy implements ShipmentTrackingStrategy {

    private final CjLogisticsClient cjLogisticsClient;
    private final ObjectMapper objectMapper;

    @Override
    public Shipment tracking(String trackingNo) {
        CjApiResponse deliveryDetail = cjLogisticsClient.getDeliveryDetail(trackingNo);

        if(!deliveryDetail.isSuccess()) {
            throw new NonExistentTrackingNumberException(trackingNo);
        }

        WblNoOutput wblNoOutput = objectMapper.convertValue(
            deliveryDetail.getData().get("wblNoOutput"), WblNoOutput.class);

        CjApiResponse deliveryProgressInfo = cjLogisticsClient.getDeliveryProgressInfo(
            trackingNo);

        List<ScanInfoOutput> scanInfoOutput =
            objectMapper.convertValue(deliveryProgressInfo.getData().get("scanInfoOutput"), new TypeReference<>() { });

        return toDomain(wblNoOutput, scanInfoOutput);
    }

    private Shipment toDomain(WblNoOutput wblNoOutput, List<ScanInfoOutput> scanInfoOutputs) {
        List<ShipmentProgress> shipmentProgress = Collections.emptyList();

        if(scanInfoOutputs != null) {
            shipmentProgress = scanInfoOutputs.stream()
                    .map(
                            scanInfoOutput -> ShipmentProgress.builder()
                                    .location(scanInfoOutput.getBranNm())
                                    .status(ShipmentStatus.matchedStatus(scanInfoOutput.getBasisSclsfCdNm()))
                                    .detailStatus(scanInfoOutput.getBasisSclsfCdNm())
                                    .processingDateTime(
                                            DateUtil.parse(
                                                    String.format("%s %s", scanInfoOutput.getScanDt(),
                                                            scanInfoOutput.getScanHms()),
                                                    CourierCompany.CJ_LOGISTICS.getDateFormatPattern()))
                                    .build())
                    .sorted(Comparator.comparing(ShipmentProgress::getProcessingDateTime))
                    .toList();
        }

        return Shipment.builder()
            .trackingNo(wblNoOutput.getWblNo())
            .courierCompany(CourierCompany.CJ_LOGISTICS)
            .recipient(
                    new Recipient(
                            wblNoOutput.getRcvrNm(),
                            wblNoOutput.getRcvrTel(),
                            wblNoOutput.getRcvrAddr()
                    )
            )
            .sender(new Sender(wblNoOutput.getSndrNm(), "", ""))
            .shipmentProgresses(shipmentProgress)
                .build();
    }

    @Override
    public CourierCompany getCourierCompanyName() {
        return CourierCompany.CJ_LOGISTICS;
    }
}
