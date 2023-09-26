package com.gnnny.parcelwizard.application;

import com.gnnny.parcelwizard.domain.shipmenttracking.ShipmentTrackingProgress;

public record ShipmentTrackingProgressResult(
    String location,
    String status,
    String detailStatus,
    String processingDateTime) {

    public ShipmentTrackingProgressResult(ShipmentTrackingProgress shipmentTrackingProgress) {
        this(
            shipmentTrackingProgress.getLocation(),
            shipmentTrackingProgress.getStatus().name(),
            shipmentTrackingProgress.getDetailStatus(),
            shipmentTrackingProgress.getProcessingDateTime().toString()
        );
    }
}
