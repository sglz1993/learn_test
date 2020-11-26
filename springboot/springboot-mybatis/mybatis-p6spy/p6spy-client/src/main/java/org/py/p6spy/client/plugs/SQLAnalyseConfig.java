package org.py.p6spy.client.plugs;

import org.apache.commons.lang3.StringUtils;

public class SQLAnalyseConfig {
    
    static boolean init = false;

    static String serviceName = "";

    public SQLAnalyseConfig(String serviceName) {
        init = true;
        if(StringUtils.isNotBlank(serviceName)) {
            SQLAnalyseConfig.serviceName = serviceName;
        }
    }
}
