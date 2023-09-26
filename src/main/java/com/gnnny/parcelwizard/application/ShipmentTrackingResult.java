package com.gnnny.parcelwizard.application;

import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTracking;
import java.util.List;

public record ShipmentTrackingResult(
    String trackingNo, String ParcelCompanyName, String senderName,
    String senderAddress, String senderPhoneNumber,
    String recipientName, String recipientAddress,
    String recipientPhoneNumber,
    List<ShipmentTrackingProgressResult> shipmentTrackingProgressResults
) {

    ShipmentTrackingResult(ShipmentTracking shipmentTracking) {
        this(
            shipmentTracking.getTrackingNo(),
            shipmentTracking.getCourierCompany().name(),
            shipmentTracking.getSender().getName(),
            shipmentTracking.getSender().getAddress(),
            shipmentTracking.getSender().getPhoneNumber(),
            shipmentTracking.getRecipient().getName(),
            shipmentTracking.getRecipient().getAddress(),
            shipmentTracking.getRecipient().getPhoneNumber(),
            shipmentTracking.getShipmentTrackingProgresses().stream()
                .map(ShipmentTrackingProgressResult::new)
                .toList()
        );
    }
}
