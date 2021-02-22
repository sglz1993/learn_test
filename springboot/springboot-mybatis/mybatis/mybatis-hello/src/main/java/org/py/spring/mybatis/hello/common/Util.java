package org.py.spring.mybatis.hello.common;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: pengyue.du
 * @time: 2020/7/27 7:54 下午
 */
public class Util {

    public static <K, V> Map<K, V> asMap(Object... kvPairs) {
        if (null == kvPairs) {
            return Collections.emptyMap();
        }

        if (0 != kvPairs.length % 2) {
            throw new RuntimeException("unmatched KV number: " + Arrays.toString(kvPairs));
        }

        Map<K, V> map = new HashMap<>();
        for (int i = 0; i < kvPairs.length / 2; i++) {
            map.put((K) kvPairs[2 * i], (V) kvPairs[2 * i + 1]);
        }
        return map;
    }

}
