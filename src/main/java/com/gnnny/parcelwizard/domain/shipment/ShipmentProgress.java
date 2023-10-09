package com.gnnny.parcelwizard.domain.shipment;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShipmentProgress {

    private ShipmentProgressId shipmentProgressId;
    private String location;
    private ShipmentStatus status;
    private String detailStatus;
    private LocalDateTime processingDateTime;

    @Builder
    public ShipmentProgress(String location, ShipmentStatus status,
        String detailStatus,
        LocalDateTime processingDateTime) {
        this.location = location;
        this.status = status;
        this.detailStatus = detailStatus;
        this.processingDateTime = processingDateTime;
    }

    @Getter
    public static class ShipmentProgressId {

        private Long shipmentProgressId;
    }
}
