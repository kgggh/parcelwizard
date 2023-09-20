package com.gnnny.parcelwizard.application;

import com.gnnny.parcelwizard.domain.delivery.Delivery;
import java.util.List;

public record ParcelDeliveryResult(
    String trackingNo, String ParcelCompanyName, String senderName,
    String senderAddress, String senderPhoneNumber,
    String recipientName, String recipientAddress,
    String recipientPhoneNumber,
    List<ParcelDeliveryProgressResult> parcelDeliveryProgressResults
) {

    ParcelDeliveryResult(Delivery delivery) {
        this(
            delivery.getTrackingNo(),
            delivery.getDeliveryCompany().name(),
            delivery.getSender().getName(),
            delivery.getSender().getAddress(),
            delivery.getSender().getPhoneNumber(),
            delivery.getRecipient().getName(),
            delivery.getRecipient().getAddress(),
            delivery.getRecipient().getPhoneNumber(),
            delivery.getDeliveryProgresses().stream()
                .map(ParcelDeliveryProgressResult::new)
                .toList()
        );
    }
}
