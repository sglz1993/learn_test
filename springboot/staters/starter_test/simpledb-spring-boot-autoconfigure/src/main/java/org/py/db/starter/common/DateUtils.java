package org.py.db.starter.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: pengyue.du
 * @time: 2020/7/28 1:23 下午
 */
public class DateUtils {

    public static final String pattern_yMDHms = "yyyy-MM-dd HH:mm:ss";

    public static Date getNowDate() {
        return new Date();
    }

    public static String formatyMDHms(Date date) {
        return new SimpleDateFormat(pattern_yMDHms).format(date);
    }

}
