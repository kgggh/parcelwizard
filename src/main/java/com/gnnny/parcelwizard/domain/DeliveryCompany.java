package com.gnnny.parcelwizard.domain;

import lombok.Getter;

@Getter
public enum DeliveryCompany {
    KOREA_POST("우체국 택배", "KR"),
    CJ_LOGISTICS("CJ 대한통운", "KR"),
    WINION_LOGIS("위니온로지스", "KR");

    private final String value;
    private final String nationality;

    DeliveryCompany(String value, String nationality) {
        this.value = value;
        this.nationality = nationality;
    }
}
