package com.gnnny.parcelwizard.domain.cargoclearace;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CargoClearanceProgress {

    private String status;
    private String detailStatus;
    private LocalDateTime datetime;
}
