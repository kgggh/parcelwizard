package com.gnnny.parcelwizard.domain.shipmenttracking;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentTrackingProgress {
    private String location;
    private ShipmentTrackingStatus status;
    private String detailStatus;
    private LocalDateTime processingDateTime;

    @Builder
    public ShipmentTrackingProgress(String location, ShipmentTrackingStatus status, String detailStatus,
        LocalDateTime processingDateTime) {
        this.location = location;
        this.status = status;
        this.detailStatus = detailStatus;
        this.processingDateTime = processingDateTime;
    }
}
