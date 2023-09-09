package com.gnnny.parcelwizard.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CargoClearance {

    private CargoClearanceId cargoClearanceId;
    private String houseBlNo;
    private List<CargoClearanceProgress> cargoClearanceProgresses = new ArrayList<>();
    private Delivery delivery;
    private String weight;

    public record CargoClearanceId(Long CargoId) {

    }
}
