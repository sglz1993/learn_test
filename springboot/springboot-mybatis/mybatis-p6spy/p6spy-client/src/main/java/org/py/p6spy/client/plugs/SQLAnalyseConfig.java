package org.py.p6spy.client.plugs;

import org.apache.commons.lang3.StringUtils;

/**
 * current config
 */
public class SQLAnalyseConfig {
    
    static boolean init = false;

    static String serviceName = "";

    static int samplingFrequency = 1;

    public SQLAnalyseConfig() {
    }

    public SQLAnalyseConfig(String serviceName, int samplingFrequency) {
        init = true;
        setServiceName(serviceName);
        setSamplingFrequency(samplingFrequency);
    }

    public static void setServiceName(String serviceName) {
        if(StringUtils.isNotBlank(serviceName)) {
            SQLAnalyseConfig.serviceName = serviceName;
        }
    }

    public static void setSamplingFrequency(int samplingFrequency) {
        if(samplingFrequency > 0) {
            SQLAnalyseConfig.samplingFrequency = samplingFrequency;
        }else {
            throw new IllegalArgumentException("samplingFrequency must is positive integer");
        }
    }
}
