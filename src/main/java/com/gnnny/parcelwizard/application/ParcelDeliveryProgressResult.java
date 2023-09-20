package com.gnnny.parcelwizard.application;

import com.gnnny.parcelwizard.domain.delivery.DeliveryProgress;

public record ParcelDeliveryProgressResult(String location, String status, String detailStatus,
                                           String processingDateTime) {

    public ParcelDeliveryProgressResult(DeliveryProgress deliveryProgress) {
        this(
            deliveryProgress.getLocation(),
            deliveryProgress.getStatus().name(),
            deliveryProgress.getDetailStatus(),
            deliveryProgress.getProcessingDateTime().toString()
        );
    }
}
