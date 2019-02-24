package com.kap.indicators.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author Konstantinos Antoniou
 */
public class DateUtil {

    private static final ZoneId UTC = ZoneId.of("UTC");

    private static final DateTimeFormatter UTC_TIMESTAMP =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSS", Locale.UK).withZone(UTC);

    private DateUtil() {
        // Intentionally Blank
    }

    public static Instant instantFromUtcTimestamp(String string) {
        return ZonedDateTime.parse(string, UTC_TIMESTAMP).toInstant();
    }
}
