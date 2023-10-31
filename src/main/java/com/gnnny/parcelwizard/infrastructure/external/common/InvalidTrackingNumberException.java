package com.gnnny.parcelwizard.infrastructure.external.common;

public class InvalidTrackingNumberException extends RuntimeException {

    public InvalidTrackingNumberException(int digit) {
        super(String.format("유효하지 않는 운송장번호 입니다.(%d자리) ", digit));
    }
}
