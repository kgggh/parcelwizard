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

    private CargoClearanceId cargoClearanceId;
    private String houseBlNo;
    private List<CustomsClearanceProgress> customsClearanceProgresses = new ArrayList<>();
    private Weight weight;

    @Builder
    public CustomsClearance(CargoClearanceId cargoClearanceId, String houseBlNo,
        List<CustomsClearanceProgress> customsClearanceProgresses, Weight weight) {
        this.cargoClearanceId = cargoClearanceId;
        this.houseBlNo = houseBlNo;
        this.customsClearanceProgresses = customsClearanceProgresses;
        this.weight = weight;
    }

    public class CargoClearanceId {
        private Long cargoClearanceId;
    }
}
