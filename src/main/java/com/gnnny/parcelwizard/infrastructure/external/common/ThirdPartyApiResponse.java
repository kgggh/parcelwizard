package com.gnnny.parcelwizard.infrastructure.external.common;

public interface ThirdPartyApiResponse {
    boolean isSuccess();
    String getErrorMessage();
}
