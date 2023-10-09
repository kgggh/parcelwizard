package com.gnnny.parcelwizard.presentation.shipment.resource;

import com.gnnny.parcelwizard.application.shipment.model.ShipmentProgressDto;

public record ShipmentProgressTrackingResource(
    String location,
    String status,
    String detailStatus,
    String processingDateTime) {

    public ShipmentProgressTrackingResource(ShipmentProgressDto shipmentProgressDto) {
        this(
            shipmentProgressDto.location(),
            shipmentProgressDto.status().toString(),
            shipmentProgressDto.detailStatus(),
            shipmentProgressDto.processingDateTime().toString()
        );
    }
}
