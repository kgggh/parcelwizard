package com.gnnny.parcelwizard.domain.cargoclearace;

import lombok.Getter;

@Getter
public enum WeightUnit {
    KG("Kilogram"),
    G("Gram"),
    LB("Pound");

    private final String value;

    WeightUnit(String value) {
        this.value = value;
    }
}
