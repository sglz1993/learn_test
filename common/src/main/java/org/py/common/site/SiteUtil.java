package org.py.common.site;

import org.py.common.util.Util;

import java.time.LocalDateTime;
import java.util.Map;

public class SiteUtil {

    public static Map<String, String> nowSite() {
        return Util.asMap("now", LocalDateTime.now().toString());
    }

}
