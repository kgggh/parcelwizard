package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTracking;
import com.gnnny.parcelwizard.domain.shipmenttracking.CourierCompany;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTrackingProgress;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTrackingStatus;
import com.gnnny.parcelwizard.domain.shipmenttracking.Recipient;
import com.gnnny.parcelwizard.domain.shipmenttracking.Sender;
import com.gnnny.parcelwizard.domain.shipmenttracking.service.ShipmentTrackingStrategy;
import com.gnnny.parcelwizard.infrastructure.external.winionlogis.WinionLogisApiResponse.ProgressInfo;
import com.gnnny.parcelwizard.infrastructure.external.winionlogis.WinionLogisApiResponse.RecipientInfo;
import com.gnnny.parcelwizard.shared.DateUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WinionLogisDeliveryStrategy implements ShipmentTrackingStrategy {

    private final WinionLogisClient winionLogisClient;

    @Override
    public ShipmentTracking tracking(String trackingNo) {
        try {
            WinionLogisApiResponse winionLogisApiResponse = winionLogisClient.getDeliveryProgressInfo(
                new DeliveryProgressSearchRequestDto(trackingNo));

            return toDomain(trackingNo, winionLogisApiResponse);
        } catch (IllegalArgumentException | NullPointerException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private ShipmentTracking toDomain(String trackingNo, WinionLogisApiResponse winionLogisApiResponse) {
        RecipientInfo recipientInfo = winionLogisApiResponse.getRecipientInfo();
        List<ProgressInfo> progressInfos = winionLogisApiResponse.getProgressInfo();

        return ShipmentTracking.builder()
            .trackingNo(trackingNo)
            .courierCompany(CourierCompany.WINION_LOGIS)
            .recipient(
                new Recipient(recipientInfo.getReceiverName(), recipientInfo.getReceiverMobile(),
                    recipientInfo.getReceiverAddr())
            )
            .sender(new Sender("", "", ""))
            .shipmentTrackingProgresses(progressInfos.stream()
                .map(
                    progressInfo -> ShipmentTrackingProgress.builder()
                        .location(progressInfo.getLocNm())
                        .status(ShipmentTrackingStatus.matchedStatus(progressInfo.getSmartStatNm()))
                        .detailStatus(progressInfo.getStatNm())
                        .processingDateTime(
                            DateUtil.parse(progressInfo.getWorkDt(), "yyyy-MM-dd HH:mm:ss")
                        )
                        .build()).toList()
            ).build();
    }

    @Override
    public CourierCompany getCourierCompanyName() {
        return CourierCompany.WINION_LOGIS;
    }
}
