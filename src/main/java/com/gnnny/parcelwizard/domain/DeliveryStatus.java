package com.gnnny.parcelwizard.domain;

import lombok.Getter;

@Getter
public enum DeliveryStatus {
    PENDING("배송 준비 중"),
    IN_TRANSIT("배송 중"),
    DELIVERED("배송 완료"),
    FAILED("배송 실패");

    private final String value;

    DeliveryStatus(String value) {
        this.value = value;
    }
}
