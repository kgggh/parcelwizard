package com.gnnny.parcelwizard.domain.cargoclearace;

import com.gnnny.parcelwizard.domain.delivery.Delivery;
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

    public class CargoClearanceId {
        private Long CargoId;
    }
}
