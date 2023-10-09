package com.gnnny.parcelwizard.presentation.shipment.resource;

import com.gnnny.parcelwizard.application.shipment.model.ShipmentDto;
import java.util.List;

public record ShipmentTrackingResource(
    String trackingNo,
    String courierCompany,
    String senderName,
    String senderAddress,
    String senderPhoneNumber,
    String recipientName,
    String recipientAddress,
    String recipientPhoneNumber,
    List<ShipmentProgressTrackingResource> shipmentProgressTrackingResources) {

    public ShipmentTrackingResource(ShipmentDto shipmentDto) {
        this(
            shipmentDto.trackingNo(),
        shipmentDto.courierCompany().toString(),
        shipmentDto.senderName(),
        shipmentDto.senderAddress(),
        shipmentDto.senderPhoneNumber(),
        shipmentDto.recipientName(),
        shipmentDto.recipientAddress(),
        shipmentDto.recipientPhoneNumber(),
        shipmentDto.shipmentProgressDtos().stream().map(ShipmentProgressTrackingResource::new)
            .toList()
        );
    }
}
