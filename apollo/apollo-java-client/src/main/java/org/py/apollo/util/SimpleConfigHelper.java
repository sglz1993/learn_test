package org.py.apollo.util;

import com.ctrip.framework.apollo.core.ConfigConsts;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.py.apollo.config.Config;
import org.py.apollo.config.ConfigMonitor;
import org.py.apollo.config.SystemConfig;
import org.py.apollo.logutl.LogbackConfiguration;

import java.util.List;

/**
 * @author: pengyue.du
 * @time: 2020/7/23 10:25 上午
 */
public class SimpleConfigHelper {

    private static volatile Config CONFIG;

    private static volatile ConfigMonitor MONITOR;

    private static volatile LogbackConfiguration LOGBACK;


    public static Config buildConfig(List<String> namespaces) {
        return buildConfig(namespaces, SystemConfig.EXPIRE_HOURS, SystemConfig.EXPIRE_MINUTES, SystemConfig.SYNC_CACHE_FILE);
    }

    public static Config buildConfig(List<String> namespaces, int expireHours, int expireMinutes) {
        return buildConfig(namespaces, expireHours, expireMinutes, SystemConfig.SYNC_CACHE_FILE);
    }

    /**
     * get config and add monitor
     * @param namespaces
     * @return
     */
    public static Config buildConfig(List<String> namespaces, int expireHours, int expireMinutes, boolean syncCacheFile) {
        if(CollectionUtils.isEmpty(namespaces)){
            namespaces = Lists.newArrayList(ConfigConsts.NAMESPACE_APPLICATION);
        }
        if (CONFIG == null) {
            synchronized (SimpleConfigHelper.class) {
                if (CONFIG == null) {
                    CONFIG = new Config(namespaces);
                }
                MONITOR = new ConfigMonitor(namespaces, expireHours, expireMinutes, syncCacheFile);
                LOGBACK = new LogbackConfiguration(namespaces);
            }
        }
        return CONFIG;
    }

}
