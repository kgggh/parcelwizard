package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.gnnny.parcelwizard.application.shipment.ShipmentTrackingStrategy;
import com.gnnny.parcelwizard.domain.shipment.*;
import com.gnnny.parcelwizard.infrastructure.external.common.NonExistentTrackingNumberException;
import com.gnnny.parcelwizard.infrastructure.external.winionlogis.WinionLogisApiResponse.ProgressInfo;
import com.gnnny.parcelwizard.infrastructure.external.winionlogis.WinionLogisApiResponse.RecipientInfo;
import com.gnnny.parcelwizard.shared.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class WinionLogisDeliveryStrategy implements ShipmentTrackingStrategy {

    private final WinionLogisClient winionLogisClient;

    @Override
    public Shipment tracking(String trackingNo) {
        WinionLogisApiResponse winionLogisApiResponse = winionLogisClient.getDeliveryProgressInfo(
            new DeliveryProgressSearchRequest(trackingNo));

        if(!winionLogisApiResponse.isSuccess()) {
            throw new NonExistentTrackingNumberException(trackingNo);
        }

        return toDomain(trackingNo, winionLogisApiResponse);
    }

    private Shipment toDomain(String trackingNo, WinionLogisApiResponse winionLogisApiResponse) {
        RecipientInfo recipientInfo = winionLogisApiResponse.getRecipientInfo();
        List<ProgressInfo> progressInfos = winionLogisApiResponse.getProgressInfo();

        return Shipment.builder()
            .trackingNo(trackingNo)
            .courierCompany(CourierCompany.WINION_LOGIS)
            .recipient(
                new Recipient(recipientInfo.getReceiverName(), recipientInfo.getReceiverMobile(),
                    recipientInfo.getReceiverAddr())
            )
            .sender(new Sender())
            .shipmentProgresses(progressInfos.stream()
                .map(
                    progressInfo -> ShipmentProgress.builder()
                        .location(progressInfo.getLocNm())
                        .status(ShipmentStatus.matchedStatus(progressInfo.getSmartStatNm()))
                        .detailStatus(progressInfo.getStatNm())
                        .processingDateTime(
                            DateUtil.parse(progressInfo.getWorkDt(), CourierCompany.WINION_LOGIS.getDateFormatPattern()))
                        .build())
                .sorted(Comparator.comparing(ShipmentProgress::getProcessingDateTime))
                .toList()
            ).build();
    }

    @Override
    public CourierCompany getCourierCompanyName() {
        return CourierCompany.WINION_LOGIS;
    }
}
