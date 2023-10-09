package com.gnnny.parcelwizard.domain.customsclearance;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomsClearance {

    private CustomsClearanceId customsClearanceId;
    private String houseBlNo;
    private String itemName;
    private Weight weight;
    private List<CustomsClearanceProgress> customsClearanceProgresses = new ArrayList<>();

    @Builder
    public CustomsClearance(CustomsClearanceId customsClearanceId, String houseBlNo, String itemName,
        Weight weight, List<CustomsClearanceProgress> customsClearanceProgresses) {
        this.customsClearanceId = customsClearanceId;
        this.houseBlNo = houseBlNo;
        this.itemName = itemName;
        this.weight = weight;
        this.customsClearanceProgresses = customsClearanceProgresses;
    }

    @Getter
    public static class CustomsClearanceId {
        private Long id;
    }
}
