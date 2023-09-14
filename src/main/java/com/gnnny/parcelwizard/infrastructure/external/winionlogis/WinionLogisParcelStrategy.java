package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.gnnny.parcelwizard.domain.delivery.Delivery;
import com.gnnny.parcelwizard.domain.delivery.DeliveryCompany;
import com.gnnny.parcelwizard.domain.delivery.DeliveryProgress;
import com.gnnny.parcelwizard.domain.delivery.DeliveryStatus;
import com.gnnny.parcelwizard.domain.delivery.Recipient;
import com.gnnny.parcelwizard.domain.delivery.Sender;
import com.gnnny.parcelwizard.domain.delivery.service.ParcelDeliveryStrategy;
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
public class WinionLogisParcelStrategy implements ParcelDeliveryStrategy {

    private final WinionLogisClient winionLogisClient;

    @Override
    public Delivery tracking(String trackingNo) {
        try {
            WinionLogisApiResponse winionLogisApiResponse = winionLogisClient.getDeliveryProgressInfo(
                new DeliveryProgressSearchRequestDto(trackingNo));

            return toDomain(trackingNo, winionLogisApiResponse);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }

        return null;
    }

    private Delivery toDomain(String trackingNo, WinionLogisApiResponse winionLogisApiResponse) {
        RecipientInfo recipientInfo = winionLogisApiResponse.getRecipientInfo();
        List<ProgressInfo> progressInfos = winionLogisApiResponse.getProgressInfo();

        return Delivery.builder()
            .trackingNo(trackingNo)
            .deliveryCompany(DeliveryCompany.WINION_LOGIS)
            .recipient(
                new Recipient(recipientInfo.getReceiverName(), recipientInfo.getReceiverMobile(),
                    recipientInfo.getReceiverAddr())
            )
            .sender(new Sender("", "", ""))
            .deliveryProgresses(progressInfos.stream()
                .map(
                    progressInfo -> DeliveryProgress.builder()
                        .location(progressInfo.getLocNm())
                        .status(DeliveryStatus.matchedStatus(progressInfo.getSmartStatNm()))
                        .detailStatus(progressInfo.getStatNm())
                        .processingDateTime(
                            DateUtil.parse(progressInfo.getWorkDt(), "yyyy-MM-dd HH:mm:ss")
                        )
                        .build()).toList()
            ).build();
    }

    @Override
    public DeliveryCompany getParcelCompanyName() {
        return DeliveryCompany.WINION_LOGIS;
    }
}
