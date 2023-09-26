package com.gnnny.parcelwizard.infrastructure.external.cj;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnnny.parcelwizard.domain.shipmenttracking.CourierCompany;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTracking;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTrackingProgress;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTrackingStatus;
import com.gnnny.parcelwizard.domain.shipmenttracking.Recipient;
import com.gnnny.parcelwizard.domain.shipmenttracking.Sender;
import com.gnnny.parcelwizard.domain.shipmenttracking.service.ShipmentTrackingStrategy;
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
public class CjLogisticsDeliveryStrategy implements ShipmentTrackingStrategy {

    private final CjLogisticsClient cjLogisticsClient;
    private final ObjectMapper objectMapper;

    @Override
    public ShipmentTracking tracking(String trackingNo) {
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

    private ShipmentTracking toDomain(WblNoOutput wblNoOutput, List<ScanInfoOutput> scanInfoOutputs) {
        return ShipmentTracking.builder()
            .trackingNo(wblNoOutput.getWblNo())
            .courierCompany(CourierCompany.CJ_LOGISTICS)
            .recipient(
                new Recipient(wblNoOutput.getRcvrNm(), wblNoOutput.getRcvrTel(),
                    wblNoOutput.getRcvrAddr())
            )
            .sender(new Sender(wblNoOutput.getSndrNm(), "", ""))
            .shipmentTrackingProgresses(scanInfoOutputs.stream()
                .map(
                    scanInfoOutput -> ShipmentTrackingProgress.builder()
                        .location(scanInfoOutput.getBranNm())
                        .status(ShipmentTrackingStatus.matchedStatus(scanInfoOutput.getBasisSclsfCdNm()))
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
