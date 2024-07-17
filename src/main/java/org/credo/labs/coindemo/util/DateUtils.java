package org.credo.labs.coindemo.util;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAmount;

public final class DateUtils {
    public final static String DATETIME_ISO_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public final static String DATETIME_DB_PATTERN = "yyyy/MM/dd HH:mm:ss";
    public final static String DATE_ISO_PATTERN = "yyyy-MM-dd";
    public final static String TIME_ISO_PATTERN = "HH:mm:ss";

    public static Long durationAsSeconds(String duration) {
        LocalDateTime start = LocalDateTime.now();
        LocalDateTime end = start.plus(stringAsTemporalAmount(duration));
        return start.until(end, ChronoUnit.SECONDS);
    }

    public static long durationAsDays(String text) {
        return Duration.parse(text).toDays();
    }

    public static TemporalAmount stringAsTemporalAmount(String text) {
        try {
            return Period.parse(text);
        } catch (Exception e) {
            return Duration.parse(text);
        }
    }
}
