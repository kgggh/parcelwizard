package com.gnnny.parcelwizard.application.shipment.model;

import com.gnnny.parcelwizard.domain.shipment.ShipmentProgress;
import com.gnnny.parcelwizard.domain.shipment.ShipmentStatus;
import java.time.LocalDateTime;

public record ShipmentProgressDto(
    String location,
    ShipmentStatus status,
    String detailStatus,
    LocalDateTime processingDateTime) {

    public ShipmentProgressDto(ShipmentProgress shipmentProgress) {
        this(
            shipmentProgress.getLocation(),
            shipmentProgress.getStatus(),
            shipmentProgress.getDetailStatus(),
            shipmentProgress.getProcessingDateTime()
        );
    }
}
