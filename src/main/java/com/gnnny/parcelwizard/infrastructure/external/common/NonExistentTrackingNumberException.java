package com.gnnny.parcelwizard.infrastructure.external.common;

public class NonExistentTrackingNumberException extends RuntimeException {

    public NonExistentTrackingNumberException(String trackingNo) {
        super(String.format("조회되지 않는 운송장 번호(%s) 입니다.", trackingNo));
    }
}
