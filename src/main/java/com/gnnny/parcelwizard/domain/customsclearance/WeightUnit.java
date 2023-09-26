package com.gnnny.parcelwizard.domain.customsclearance;

import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
public enum WeightUnit {
    KG("Kilogram"),
    G("Gram"),
    LB("Pound");

    private final String value;

    WeightUnit(String value) {
        this.value = value;
    }

    public static WeightUnit find(String value) {
        if(Strings.isEmpty(value)) {
            return null;
        }

        return WeightUnit.valueOf(value);
    }
}
