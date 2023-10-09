package com.gnnny.parcelwizard.application.shipment.model;

import com.gnnny.parcelwizard.domain.shipment.CourierCompany;
import com.gnnny.parcelwizard.domain.shipment.Shipment;
import com.gnnny.parcelwizard.domain.shipment.Shipment.ShipmentId;
import java.util.List;

public record ShipmentDto(
    ShipmentId shipmentId, String trackingNo, CourierCompany courierCompany, String senderName,
    String senderAddress, String senderPhoneNumber,
    String recipientName, String recipientAddress,
    String recipientPhoneNumber,
    List<ShipmentProgressDto> shipmentProgressDtos
) {

    public ShipmentDto(Shipment shipment) {
        this(
            shipment.getShipmentId(),
            shipment.getTrackingNo(),
            shipment.getCourierCompany(),
            shipment.getSender().getName(),
            shipment.getSender().getAddress(),
            shipment.getSender().getPhoneNumber(),
            shipment.getRecipient().getName(),
            shipment.getRecipient().getAddress(),
            shipment.getRecipient().getPhoneNumber(),
            shipment.getShipmentProgresses().stream()
                .map(ShipmentProgressDto::new)
                .toList()
        );
    }
}
