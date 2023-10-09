package com.gnnny.parcelwizard.domain.customsclearance;

import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
public enum WeightUnit {
    KG("kg"),
    G("g"),
    LB("lb");

    private final String value;

    WeightUnit(String value) {
        this.value = value;
    }

    public static WeightUnit find(String value) {
        if(!StringUtils.hasText(value)) {
            return null;
        }

        return WeightUnit.valueOf(value);
    }
}
