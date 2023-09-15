package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.gnnny.parcelwizard.infrastructure.external.ThirdPartyApiResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class RecipientInfo {

        private String signWayDesc;
        private String receiverAddr;
        private String receiverMobile;
        private String itemNm;
        private String receiverName;
        private String rsCount;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class DriverInfo {

        private String userNm;
        private String mobile;
        private String rsCount;
        private String agencyNm;
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(value = PropertyNamingStrategy.SnakeCaseStrategy.class)
    public static class ProgressInfo {

        private String smartStatNm;
        private String locNm;
        private String smartStatLv;
        private String workDt;
        private String statNm;
    }
}
