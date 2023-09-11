package com.gnnny.parcelwizard.infrastructure.external.winionlogis;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeliveryProgressSearchRequestDto {

    @JsonProperty(value = "PROC_KIND")
    private String procKind = "selTrackingInfoUser";

    @JsonProperty(value = "tracking_no")
    private String trackingNo;

    public DeliveryProgressSearchRequestDto(String trackingNo) {
        this.trackingNo = trackingNo;
    }
}
