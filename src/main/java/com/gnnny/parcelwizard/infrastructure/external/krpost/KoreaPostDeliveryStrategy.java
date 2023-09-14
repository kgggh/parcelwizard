package com.gnnny.parcelwizard.infrastructure.external.krpost;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gnnny.parcelwizard.domain.delivery.Delivery;
import com.gnnny.parcelwizard.domain.delivery.DeliveryCompany;
import com.gnnny.parcelwizard.domain.delivery.DeliveryProgress;
import com.gnnny.parcelwizard.domain.delivery.DeliveryStatus;
import com.gnnny.parcelwizard.domain.delivery.Recipient;
import com.gnnny.parcelwizard.domain.delivery.Sender;
import com.gnnny.parcelwizard.domain.delivery.service.ParcelDeliveryStrategy;
import com.gnnny.parcelwizard.infrastructure.external.krpost.KoreaPostApiResponse.DeliveryDetail;
import com.gnnny.parcelwizard.shared.DateUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KoreaPostDeliveryStrategy implements ParcelDeliveryStrategy {

    private final KoreaPostClient koreaPostClient;
    private final ObjectMapper objectMapper;

    @Override
    public Delivery tracking(String trackingNo) {
        try {
            KoreaPostApiResponse koreaPostApiResponse =
                koreaPostClient.getDeliveryProgressInfo(trackingNo);

            return toDomain(koreaPostApiResponse);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private Delivery toDomain(KoreaPostApiResponse koreaPostApiResponse) {
        DeliveryDetail deliveryDetail = koreaPostApiResponse.getDeliveryDetail();
        List<KoreaPostApiResponse.DeliveryProgress> deliveryProgresses = koreaPostApiResponse.getDeliveryProgresses();
        return Delivery.builder()
            .trackingNo(deliveryDetail.getTrackingNo())
            .deliveryCompany(DeliveryCompany.KOREA_POST)
            .recipient(
                new Recipient(deliveryDetail.getRecipientName(), "",
                    "")
            )
            .sender(new Sender(deliveryDetail.getSenderName(), "", ""))
            .deliveryProgresses(deliveryProgresses.stream()
                .map(
                    deliveryProgress -> DeliveryProgress.builder()
                        .location(deliveryProgress.getLocation())
                        .status(DeliveryStatus.matchedStatus(deliveryProgress.getStatus()))
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
    public DeliveryCompany getParcelCompanyName() {
        return DeliveryCompany.KOREA_POST;
    }

}
