package com.gnnny.parcelwizard.domain.delivery;

import lombok.Getter;

@Getter
public enum DeliveryCompany {
    KOREA_POST("우체국택배", "KR"),
    CJ_LOGISTICS("CJ대한통운", "KR"),
    WINION_LOGIS("위니온로지스", "KR");

    private final String value;
    private final String nationality;

    DeliveryCompany(String value, String nationality) {
        this.value = value;
        this.nationality = nationality;
    }
}
