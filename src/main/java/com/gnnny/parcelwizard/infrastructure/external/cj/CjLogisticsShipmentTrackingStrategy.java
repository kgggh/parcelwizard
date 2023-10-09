package com.gnnny.parcelwizard.infrastructure.external.cj;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnnny.parcelwizard.domain.shipment.CourierCompany;
import com.gnnny.parcelwizard.domain.shipment.Shipment;
import com.gnnny.parcelwizard.domain.shipment.ShipmentProgress;
import com.gnnny.parcelwizard.domain.shipment.ShipmentStatus;
import com.gnnny.parcelwizard.domain.shipment.Recipient;
import com.gnnny.parcelwizard.domain.shipment.Sender;
import com.gnnny.parcelwizard.application.shipment.ShipmentTrackingStrategy;
import com.gnnny.parcelwizard.infrastructure.external.cj.CjApiResponse.ScanInfoOutput;
import com.gnnny.parcelwizard.infrastructure.external.cj.CjApiResponse.WblNoOutput;
import com.gnnny.parcelwizard.shared.DateUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CjLogisticsShipmentTrackingStrategy implements ShipmentTrackingStrategy {

    private final CjLogisticsClient cjLogisticsClient;
    private final ObjectMapper objectMapper;

    @Override
    public Shipment tracking(String trackingNo) {
        try {
            CjApiResponse deliveryDetail = cjLogisticsClient.getDeliveryDetail(trackingNo);

            WblNoOutput wblNoOutput = objectMapper.convertValue(
                deliveryDetail.getData().get("wblNoOutput"), WblNoOutput.class);

            CjApiResponse deliveryProgressInfo = cjLogisticsClient.getDeliveryProgressInfo(
                trackingNo);
            List<ScanInfoOutput> scanInfoOutput =
                objectMapper.convertValue(deliveryProgressInfo.getData().get("scanInfoOutput"),
                    new TypeReference<>() {
                    });

            return toDomain(wblNoOutput, scanInfoOutput);
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error(e.getMessage(), e);
            throw e;
        }

    }

    private Shipment toDomain(WblNoOutput wblNoOutput, List<ScanInfoOutput> scanInfoOutputs) {
        return Shipment.builder()
            .trackingNo(wblNoOutput.getWblNo())
            .courierCompany(CourierCompany.CJ_LOGISTICS)
            .recipient(
                new Recipient(wblNoOutput.getRcvrNm(), wblNoOutput.getRcvrTel(),
                    wblNoOutput.getRcvrAddr())
            )
            .sender(new Sender(wblNoOutput.getSndrNm(), "", ""))
            .shipmentProgresses(scanInfoOutputs.stream()
                .map(
                    scanInfoOutput -> ShipmentProgress.builder()
                        .location(scanInfoOutput.getBranNm())
                        .status(ShipmentStatus.matchedStatus(scanInfoOutput.getBasisSclsfCdNm()))
                        .detailStatus(scanInfoOutput.getBasisSclsfCdNm())
                        .processingDateTime(
                            DateUtil.parse(
                                String.format("%s %s", scanInfoOutput.getScanDt(),
                                    scanInfoOutput.getScanHms()),
                                "yyyy-MM-dd HH:mm:ss")
                        )
                        .build()).toList()
            ).build();
    }

    @Override
    public CourierCompany getCourierCompanyName() {
        return CourierCompany.CJ_LOGISTICS;
    }

}
