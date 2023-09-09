package com.gnnny.parcelwizard.domain;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CargoClearanceProgress {
    private String status;
    private LocalDateTime datetime;

    @Builder
    public CargoClearanceProgress(String status, LocalDateTime datetime) {
        this.status = status;
        this.datetime = datetime;
    }
}
