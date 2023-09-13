package com.gnnny.parcelwizard.domain.delivery;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    private DeliveryId deliveryId;
    private String trackingNo;
    private DeliveryCompany deliveryCompany;
    private Recipient recipient;
    private Sender sender;
    private List<DeliveryProgress> deliveryProgresses = new ArrayList<>();

    @Builder
    public Delivery(String trackingNo, DeliveryCompany deliveryCompany, Recipient recipient,
        Sender sender, List<DeliveryProgress> deliveryProgresses) {
        this.trackingNo = trackingNo;
        this.deliveryCompany = deliveryCompany;
        this.recipient = recipient;
        this.sender = sender;
        this.deliveryProgresses = deliveryProgresses;
    }

    public class DeliveryId {
        private Long deliveryId;
    }
}
