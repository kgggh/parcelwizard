package com.gnnny.parcelwizard.presentation.customsclearance.resource;

import com.gnnny.parcelwizard.application.customsclearance.model.CustomsClearanceDto;
import java.util.List;

public record CustomsClearanceTrackingResource(
    String houseBlNo,
    String itemName,
    Double weight,
    String weightUnit,
    List<CustomsClearanceProgressTrackingResource> customsClearanceProgressTrackingResources) {

    public CustomsClearanceTrackingResource(CustomsClearanceDto customsClearanceDto) {
        this(
            customsClearanceDto.houseBlNo(),
            customsClearanceDto.itemName(),
            customsClearanceDto.weight().getValue(),
            customsClearanceDto.weight().getUnit().name(),
            customsClearanceDto.customsClearanceProgressDtos().stream()
                .map(CustomsClearanceProgressTrackingResource::new)
                .toList()
        );
    }
}
