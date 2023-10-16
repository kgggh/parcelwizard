package com.gnnny.parcelwizard.domain.customsclearance;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomsClearanceProgress {

    private CustomsClearanceProgressId customsClearanceProgressId;
    private String status;
    private String detailStatus;
    private LocalDateTime processingDateTime;

    @Builder
    public CustomsClearanceProgress(CustomsClearanceProgressId customsClearanceProgressId,
        String status, String detailStatus, LocalDateTime processingDateTime) {
        this.customsClearanceProgressId = customsClearanceProgressId;
        this.status = status;
        this.detailStatus = detailStatus;
        this.processingDateTime = processingDateTime;
    }

    @Getter
    public static class CustomsClearanceProgressId {

        private Long id;
    }
}
