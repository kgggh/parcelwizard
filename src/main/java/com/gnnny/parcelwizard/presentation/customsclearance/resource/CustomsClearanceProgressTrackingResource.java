package com.gnnny.parcelwizard.presentation.customsclearance.resource;

import com.gnnny.parcelwizard.application.customsclearance.model.CustomsClearanceProgressDto;

public record CustomsClearanceProgressTrackingResource(
    String status,
    String detailStatus,
    String processingDateTime) {

    public CustomsClearanceProgressTrackingResource(
        CustomsClearanceProgressDto customsClearanceProgressDto) {
        this(
            customsClearanceProgressDto.status(),
            customsClearanceProgressDto.detailStatus(),
            customsClearanceProgressDto.processingDateTime().toString()
        );
    }
}
