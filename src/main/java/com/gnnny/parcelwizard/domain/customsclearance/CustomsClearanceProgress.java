package com.gnnny.parcelwizard.domain.customsclearance;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CustomsClearanceProgress {

    private String status;
    private String detailStatus;
    private LocalDateTime datetime;
}
