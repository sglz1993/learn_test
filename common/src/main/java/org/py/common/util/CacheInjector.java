package org.py.common.util;

import org.py.common.internals.ServiceBootstrap;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author pengyue.du
 */
public class CacheInjector {

    private static volatile Map<Class, Object> cacheMap = new ConcurrentHashMap<>();
    private static final Object lock = new Object();

    public static <T> T getInjector(Class<T> clazz) {
        if (cacheMap.get(clazz) == null) {
            synchronized (lock) {
                if (cacheMap.get(clazz) == null) {
                    cacheMap.put(clazz, ServiceBootstrap.loadFirst(clazz));
                }
            }
        }
        return (T) cacheMap.get(clazz);
    }
}
