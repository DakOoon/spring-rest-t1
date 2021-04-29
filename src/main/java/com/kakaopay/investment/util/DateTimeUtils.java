package com.kakaopay.investment.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final DateTimeFormatter PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format(LocalDateTime target) {
        if(target == null) {
            return null;
        }
        return target.format(PATTERN);
    }
}
