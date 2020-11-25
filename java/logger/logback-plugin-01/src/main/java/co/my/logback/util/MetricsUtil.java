package co.my.logback.util;

import io.prometheus.client.Counter;

public class MetricsUtil {

    private static final Counter ASYNC_LOGBACK_FAILURE_COUNTER = Counter.build().name("async_logback_failure").help("async logback failure").register();

    public static void record() {
        ASYNC_LOGBACK_FAILURE_COUNTER.inc();
    }

}
