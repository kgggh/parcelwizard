package com.gnnny.parcelwizard.infrastructure.external.krpost;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class KoreaPostApiResponse {

    private DeliveryDetail deliveryDetail;
    private List<DeliveryProgress> deliveryProgresses;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeliveryProgress {

        private String processingDate;
        private String processingTime;
        private String location;
        private String status;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class DeliveryDetail {

        private String trackingNo;
        private String senderName;
        private String recipientName;
    }
}
