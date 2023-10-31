package com.gnnny.parcelwizard.infrastructure.external.common;

public class ThirdPartyAPIConnectionException extends RuntimeException {

    public ThirdPartyAPIConnectionException(Throwable throwable) {
        super("택배/통관사측 서버문제로 조회가 불가능 합니다.", throwable);
    }
}
