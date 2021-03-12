package org.py.common.cache;

import java.util.HashMap;
import java.util.function.Function;

/**
 * 参考自：{@link org.springframework.util.function.SingletonSupplier}
 *
 * @author pengyue.du
 */
public class SingletonFunction<K, V> {

    private final Function<K, ? extends V> instanceFunction;

    private final HashMap<K, V> cache = new HashMap<>();

    public SingletonFunction(Function<K, ? extends V> instanceFunction) {
        this.instanceFunction = instanceFunction;
    }

    public static <K, V> SingletonFunction of(Function<K, ? extends V> instanceFunction) {
        return new SingletonFunction(instanceFunction);
    }

    public V get(K key) {
        V instance = cache.get(key);
        if (instance == null) {
            synchronized (this) {
                instance = cache.get(key);
                if (instance == null) {
                    if (this.instanceFunction != null) {
                        instance = instanceFunction.apply(key);
                    }
                    cache.put(key, instance);
                }
            }
        }
        return instance;
    }

}
