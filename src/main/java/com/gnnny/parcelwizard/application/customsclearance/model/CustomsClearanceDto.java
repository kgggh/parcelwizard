package com.gnnny.parcelwizard.application.customsclearance.model;

import com.gnnny.parcelwizard.domain.customsclearance.CustomsClearance;
import com.gnnny.parcelwizard.domain.customsclearance.CustomsClearance.CustomsClearanceId;
import com.gnnny.parcelwizard.domain.customsclearance.Weight;
import java.util.List;

public record CustomsClearanceDto(
    CustomsClearanceId customsClearanceId,
    String houseBlNo,
    String itemName,
    Weight weight,
    List<CustomsClearanceProgressDto> customsClearanceProgressDtos) {

    public CustomsClearanceDto(CustomsClearance customsClearance) {
        this(
            customsClearance.getCustomsClearanceId(),
            customsClearance.getHouseBlNo(),
            customsClearance.getItemName(),
            customsClearance.getWeight(),
            customsClearance.getCustomsClearanceProgresses().stream()
                .map(CustomsClearanceProgressDto::new)
                .toList()
        );
    }
}
