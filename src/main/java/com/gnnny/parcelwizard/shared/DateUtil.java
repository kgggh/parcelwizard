package com.gnnny.parcelwizard.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.experimental.UtilityClass;
import org.springframework.util.StringUtils;

@UtilityClass
public class DateUtil {

    public LocalDateTime parse(String text, String format) {
        if(!StringUtils.hasText(text)) {
            throw new IllegalArgumentException("The text to be parsed is null.");
        }
        return LocalDateTime.parse(text, DateTimeFormatter.ofPattern(format));
    }
}
