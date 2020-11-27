package org.py.p6spy.client.plugs;

import org.apache.commons.lang3.StringUtils;

/**
 * current config
 */
public class SQLAnalyseConfig {
    
    static boolean init = false;

    static String appId = "";

    static String serviceName = "";

    static int samplingFrequency = 1;

    /******************************************************
     * kafka 配置初始化完成 变更需要重新启动
     ******************************************************/

    static String bootstrapServers = "";


    public SQLAnalyseConfig() {
    }

    public SQLAnalyseConfig(String appId, String serviceName, int samplingFrequency, String bootstrapServers) {
        init = true;
        setAppId(appId);
        setServiceName(serviceName);
        setSamplingFrequency(samplingFrequency);
        setBootstrapServers(bootstrapServers);
    }

    public static void setAppId(String appId) {
        if(StringUtils.isNotBlank(appId)) {
            SQLAnalyseConfig.appId = appId;
        }else {
            throw new IllegalArgumentException("sql recorder appId is empty");
        }
    }

    public static void setServiceName(String serviceName) {
        if(StringUtils.isNotBlank(serviceName)) {
            SQLAnalyseConfig.serviceName = serviceName;
        }else {
            throw new IllegalArgumentException("sql recorder serviceName is empty");
        }
    }

    public static void setSamplingFrequency(int samplingFrequency) {
        if(samplingFrequency > 0) {
            SQLAnalyseConfig.samplingFrequency = samplingFrequency;
        }else {
            throw new IllegalArgumentException("samplingFrequency must is positive integer");
        }
    }

    public static void setBootstrapServers(String bootstrapServers) {
        if(StringUtils.isNotBlank(bootstrapServers)) {
            SQLAnalyseConfig.bootstrapServers = bootstrapServers;
        }else {
            throw new IllegalArgumentException("sql recorder bootstrapServers is empty");
        }
    }

    public static String getAppId() {
        return appId;
    }

    public static String getBootstrapServers() {
        return bootstrapServers;
    }

    public static String getServiceName() {
        return serviceName;
    }

    public static int getSamplingFrequency() {
        return samplingFrequency;
    }
}
