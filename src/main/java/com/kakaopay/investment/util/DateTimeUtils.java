package com.kakaopay.investment.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtils {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";

    public static String format(LocalDateTime target) {
        if(target == null) {
            return null;
        }
        return target.format(DateTimeFormatter.ofPattern(PATTERN));
    }
}
