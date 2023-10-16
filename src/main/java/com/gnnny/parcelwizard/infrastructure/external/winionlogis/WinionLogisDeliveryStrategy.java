package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.gnnny.parcelwizard.domain.shipment.Shipment;
import com.gnnny.parcelwizard.domain.shipment.CourierCompany;
import com.gnnny.parcelwizard.domain.shipment.ShipmentProgress;
import com.gnnny.parcelwizard.domain.shipment.ShipmentStatus;
import com.gnnny.parcelwizard.domain.shipment.Recipient;
import com.gnnny.parcelwizard.domain.shipment.Sender;
import com.gnnny.parcelwizard.application.shipment.ShipmentTrackingStrategy;
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
    public Shipment tracking(String trackingNo) {
        try {
            WinionLogisApiResponse winionLogisApiResponse = winionLogisClient.getDeliveryProgressInfo(
                new DeliveryProgressSearchRequest(trackingNo));

            if(!winionLogisApiResponse.isSuccess()) {
                throw new IllegalStateException("해당 운송장번호가 조회되지 않습니다.");
            }

            return toDomain(trackingNo, winionLogisApiResponse);
        } catch (RuntimeException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
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
            .sender(new Sender("", "", ""))
            .shipmentProgresses(progressInfos.stream()
                .map(
                    progressInfo -> ShipmentProgress.builder()
                        .location(progressInfo.getLocNm())
                        .status(ShipmentStatus.matchedStatus(progressInfo.getSmartStatNm()))
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
