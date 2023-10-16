package com.gnnny.parcelwizard.application.customsclearance.model;

import com.gnnny.parcelwizard.domain.customsclearance.CustomsClearanceProgress;
import java.time.LocalDateTime;

public record CustomsClearanceProgressDto(
    String status,
    String detailStatus,
    LocalDateTime processingDateTime) {

    public CustomsClearanceProgressDto(CustomsClearanceProgress customsClearanceProgress) {
        this(
            customsClearanceProgress.getStatus(),
            customsClearanceProgress.getDetailStatus(),
            customsClearanceProgress.getProcessingDateTime()
        );
    }
}
