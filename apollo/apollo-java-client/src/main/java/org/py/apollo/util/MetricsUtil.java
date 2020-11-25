package org.py.apollo.util;

import io.prometheus.client.Counter;

/**
 * Metrics util
 *
 * @author pengyue.du
 */
public class MetricsUtil {

    private static final Counter APOLLO_CACHE_FILE_EXPIRE_COUNTER = Counter.build()
            .name("apollo_cache_file_expire")
            .labelNames("cache_file_expire")
            .help("cache file expire").register();

    private static final Counter APOLLO_CONFIG_SERVER_CONNECT_TIMEOUT_COUNTER = Counter.build()
            .name("apollo_config_server_connect_timeout")
            .labelNames("config_server_connect_timeout")
            .help("config server connect timeout").register();


    public static void recordApolloCacheFileExpire(String errorMsg) {
        APOLLO_CACHE_FILE_EXPIRE_COUNTER.labels(errorMsg).inc();
    }

    public static void recordApolloConnectTimeout(String errorMsg) {
        APOLLO_CONFIG_SERVER_CONNECT_TIMEOUT_COUNTER.labels(errorMsg).inc();
    }


}
