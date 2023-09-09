package com.gnnny.parcelwizard.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery {

    private DeliveryId deliveryId;
    private String trackingNumber;
    private DeliveryCompany deliveryCompany;
    private Recipient recipient;
    private Sender sender;
    private List<DeliveryProgress> deliveryProgresses = new ArrayList<>();

    public record DeliveryId(Long deliveryId) {

    }
}
