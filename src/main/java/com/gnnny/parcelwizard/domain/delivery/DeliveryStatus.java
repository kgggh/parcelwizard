package com.gnnny.parcelwizard.domain.delivery;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import lombok.Getter;
import org.apache.logging.log4j.util.Strings;

@Getter
public enum DeliveryStatus {
    PENDING("배송 준비 중", List.of("접수")),
    IN_TRANSIT("배송 중", List.of("배송출발", "집화", "터미널입고", "입고", "상차")),
    DELIVERED("배송 완료", List.of("배송완료", "완료")),
    UNKOWN("알수없음", Collections.emptyList());

    private final String value;
    private final List<String> detailStatus;

    DeliveryStatus(String value, List<String> detailStatus) {
        this.value = value;
        this.detailStatus = detailStatus;
    }

    public static DeliveryStatus matchedStatus(String text) {
        if (Strings.isEmpty(text) || text.equals(" ")) {
            return PENDING;
        }

        String withoutSpacesText = text.replaceAll("\\s+", "");

        return Arrays.stream(DeliveryStatus.values())
            .filter(status -> status.getDetailStatus().contains(withoutSpacesText))
            .findAny()
            .orElse(IN_TRANSIT);
    }
}
