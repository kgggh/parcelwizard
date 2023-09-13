package com.gnnny.parcelwizard.domain.delivery;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DeliveryProgress {
    private String location;
    private DeliveryStatus status;
    private String detailStatus;
    private LocalDateTime processingDateTime;

    @Builder
    public DeliveryProgress(String location, DeliveryStatus status, String detailStatus,
        LocalDateTime processingDateTime) {
        this.location = location;
        this.status = status;
        this.detailStatus = detailStatus;
        this.processingDateTime = processingDateTime;
    }
}
