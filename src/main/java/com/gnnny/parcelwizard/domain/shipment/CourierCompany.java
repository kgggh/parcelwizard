package com.gnnny.parcelwizard.domain.shipment;

import lombok.Getter;

@Getter
public enum CourierCompany {
    KOREA_POST("우체국택배", "KR", "yyyy.MM.dd HH:mm"),
    CJ_LOGISTICS("CJ대한통운", "KR", "yyyy-MM-dd HH:mm:ss"),
    WINION_LOGIS("위니온로지스", "KR", "yyyy-MM-dd HH:mm:ss");

    private final String value;
    private final String nationality;
    private final String dateFormatPattern;

    CourierCompany(String value, String nationality, String dateFormatPattern) {
        this.value = value;
        this.nationality = nationality;
        this.dateFormatPattern = dateFormatPattern;
    }
}