package org.py.common.util;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static final String format_yyyyMMDDHHmmssZZ = "yyyy-MM-DD HH:mm:ss.SSS ZZZZ";

    public static String getStrDate() {
        return getStrDate(getNow());
    }

    public static String getStrDate(ZonedDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern(format_yyyyMMDDHHmmssZZ));
    }

    public static ZonedDateTime getNow() {
        return ZonedDateTime.now();
    }

}
