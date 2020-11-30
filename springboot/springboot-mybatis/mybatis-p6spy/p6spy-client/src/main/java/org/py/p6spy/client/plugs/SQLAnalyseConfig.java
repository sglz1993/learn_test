package org.py.p6spy.client.plugs;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.py.p6spy.client.config.Constant;

import java.util.List;

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

    static int partitions = Constant.DEFAULT_PARTITIONS;

    static int replics = Constant.DEFAULT_REPLICATION_FACTOR;


    /******************************************************
     * 以下配置 变更需要重新启动
     ******************************************************/

    static List<String> initSQL = Lists.newArrayList();

    public SQLAnalyseConfig() {
    }

    public SQLAnalyseConfig(String appId, String serviceName, int samplingFrequency, String bootstrapServers, List<String> initSQL) {
        init = true;
        setAppId(appId);
        setServiceName(serviceName);
        setSamplingFrequency(samplingFrequency);
        setBootstrapServers(bootstrapServers);
        setInitSQL(initSQL);
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

    public static int getPartitions() {
        return partitions;
    }

    public static void setPartitions(int partitions) {
        if(partitions > 0) {
            SQLAnalyseConfig.partitions = partitions;
        }else {
            throw new IllegalArgumentException("partitions must is positive integer");
        }
    }

    public static int getReplics() {
        return replics;
    }

    public static void setReplics(int replics) {
        if(replics > 0) {
            SQLAnalyseConfig.replics = replics;
        }else {
            throw new IllegalArgumentException("replics must is positive integer");
        }
    }

    public static List<String> getInitSQL() {
        return initSQL;
    }

    public static void setInitSQL(List<String> initSQL) {
        if(initSQL == null) {
            return;
        }
        SQLAnalyseConfig.initSQL = initSQL;
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
