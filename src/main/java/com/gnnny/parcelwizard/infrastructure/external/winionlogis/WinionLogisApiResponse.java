package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.gnnny.parcelwizard.infrastructure.external.common.ThirdPartyApiResponse;
import lombok.Getter;

import java.util.List;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class WinionLogisApiResponse implements ThirdPartyApiResponse {

    private Boolean rescod;
    private String resmsg;

    @JsonAlias(value = "RCVMAN")
    private RecipientInfo recipientInfo;

    @JsonAlias(value = "DRVMAN")
    private DriverInfo driverInfo;

    @JsonAlias(value = "LIST")
    private List<ProgressInfo> progressInfo;

    @Override
    public boolean isSuccess() {
        return rescod;
    }

    @Override
    public String getErrorMessage() {
        return null;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class RecipientInfo {

        private String signWayDesc;
        private String receiverAddr;
        private String receiverMobile;
        private String itemNm;
        private String receiverName;
        private String rsCount;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class DriverInfo {

        private String userNm;
        private String mobile;
        private String rsCount;
        private String agencyNm;
    }

    @Getter
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(value = PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class ProgressInfo {

        private String smartStatNm;
        private String locNm;
        private String smartStatLv;
        private String workDt;
        private String statNm;
    }
}
