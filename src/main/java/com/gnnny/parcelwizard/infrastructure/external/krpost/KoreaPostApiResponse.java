package com.gnnny.parcelwizard.infrastructure.external.krpost;

import com.gnnny.parcelwizard.infrastructure.external.common.ThirdPartyApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class KoreaPostApiResponse implements ThirdPartyApiResponse {

    private DeliveryDetail deliveryDetail;
    private List<DeliveryProgress> deliveryProgresses;

    @Override
    public boolean isSuccess() {
        return false;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Getter
    @AllArgsConstructor
    public static class DeliveryProgress {

        private String processingDate;
        private String processingTime;
        private String location;
        private String status;
    }

    @Getter
    @AllArgsConstructor
    public static class DeliveryDetail {

        private String trackingNo;
        private String senderName;
        private String recipientName;
    }
}
