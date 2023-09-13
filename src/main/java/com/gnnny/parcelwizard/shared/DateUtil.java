package com.gnnny.parcelwizard.shared;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.apache.logging.log4j.util.Strings;

public class DateUtil {

    public static LocalDateTime parse(String text) {
        if(Strings.isEmpty(text)) {
            return null;
        }

        try {
            return LocalDateTime.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } catch (DateTimeParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
