package com.gnnny.parcelwizard.infrastructure.external.krpost;

import com.gnnny.parcelwizard.application.shipment.ShipmentTrackingStrategy;
import com.gnnny.parcelwizard.domain.shipment.*;
import com.gnnny.parcelwizard.infrastructure.external.krpost.KoreaPostApiResponse.DeliveryDetail;
import com.gnnny.parcelwizard.shared.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class KoreaPostShipmentTrackingStrategy implements ShipmentTrackingStrategy {

    private final KoreaPostScrapper koreaPostScrapper;

    @Override
    public Shipment tracking(String trackingNo) {
        try {
            KoreaPostApiResponse koreaPostApiResponse =
                koreaPostScrapper.getDeliveryProgressInfo(trackingNo);

            return toDomain(koreaPostApiResponse);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            throw e;
        }
    }

    private Shipment toDomain(KoreaPostApiResponse koreaPostApiResponse) {
        DeliveryDetail deliveryDetail = koreaPostApiResponse.getDeliveryDetail();

        List<KoreaPostApiResponse.DeliveryProgress> deliveryProgresses = koreaPostApiResponse.getDeliveryProgresses();

        return Shipment.builder()
            .trackingNo(deliveryDetail.getTrackingNo())
            .courierCompany(CourierCompany.KOREA_POST)
            .recipient(new Recipient(deliveryDetail.getRecipientName(), "", ""))
            .sender(new Sender(deliveryDetail.getSenderName(), "", ""))
            .shipmentProgresses(deliveryProgresses.stream()
                .map(
                    deliveryProgress -> ShipmentProgress.builder()
                        .location(deliveryProgress.getLocation())
                        .status(ShipmentStatus.matchedStatus(deliveryProgress.getStatus()))
                        .detailStatus(deliveryProgress.getStatus())
                        .processingDateTime(
                            DateUtil.parse(
                                String.format("%s %s", deliveryProgress.getProcessingDate(),
                                    deliveryProgress.getProcessingTime()),
                                CourierCompany.KOREA_POST.getDateFormatPattern()))
                        .build())
                .sorted(Comparator.comparing(ShipmentProgress::getProcessingDateTime))
                .toList()
            ).build();
    }

    @Override
    public CourierCompany getCourierCompanyName() {
        return CourierCompany.KOREA_POST;
    }
}
