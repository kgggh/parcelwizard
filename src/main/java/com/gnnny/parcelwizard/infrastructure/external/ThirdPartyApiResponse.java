package com.gnnny.parcelwizard.infrastructure.external;

public interface ThirdPartyApiResponse {
    boolean isSuccess();
    String getErrorMessage();
}
