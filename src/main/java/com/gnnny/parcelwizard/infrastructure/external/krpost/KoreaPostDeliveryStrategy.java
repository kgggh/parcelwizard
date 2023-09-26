package com.gnnny.parcelwizard.infrastructure.external.krpost;

import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTracking;
import com.gnnny.parcelwizard.domain.shipmenttracking.CourierCompany;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTrackingProgress;
import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTrackingStatus;
import com.gnnny.parcelwizard.domain.shipmenttracking.Recipient;
import com.gnnny.parcelwizard.domain.shipmenttracking.Sender;
import com.gnnny.parcelwizard.domain.shipmenttracking.service.ShipmentTrackingStrategy;
import com.gnnny.parcelwizard.infrastructure.external.krpost.KoreaPostApiResponse.DeliveryDetail;
import com.gnnny.parcelwizard.shared.DateUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KoreaPostDeliveryStrategy implements ShipmentTrackingStrategy {

    private final KoreaPostClient koreaPostClient;

    @Override
    public ShipmentTracking tracking(String trackingNo) {
        try {
            KoreaPostApiResponse koreaPostApiResponse =
                koreaPostClient.getDeliveryProgressInfo(trackingNo);

            return toDomain(koreaPostApiResponse);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private ShipmentTracking toDomain(KoreaPostApiResponse koreaPostApiResponse) {
        DeliveryDetail deliveryDetail = koreaPostApiResponse.getDeliveryDetail();
        List<KoreaPostApiResponse.DeliveryProgress> deliveryProgresses = koreaPostApiResponse.getDeliveryProgresses();
        return ShipmentTracking.builder()
            .trackingNo(deliveryDetail.getTrackingNo())
            .courierCompany(CourierCompany.KOREA_POST)
            .recipient(
                new Recipient(deliveryDetail.getRecipientName(), "",
                    "")
            )
            .sender(new Sender(deliveryDetail.getSenderName(), "", ""))
            .shipmentTrackingProgresses(deliveryProgresses.stream()
                .map(
                    deliveryProgress -> ShipmentTrackingProgress.builder()
                        .location(deliveryProgress.getLocation())
                        .status(ShipmentTrackingStatus.matchedStatus(deliveryProgress.getStatus()))
                        .detailStatus(deliveryProgress.getStatus())
                        .processingDateTime(
                            DateUtil.parse(
                                String.format("%s %s", deliveryProgress.getProcessingDate(),
                                    deliveryProgress.getProcessingTime()),
                                "yyyy.MM.dd HH:mm")
                        )
                        .build()).toList()
            ).build();
    }

    @Override
    public CourierCompany getCourierCompanyName() {
        return CourierCompany.KOREA_POST;
    }

}
