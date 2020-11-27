package org.py.p6spy.client.util;

import com.p6spy.engine.common.Value;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Util {

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        }
    }

    public static String codingData(String data) {
        if(data == null) {
            return null;
        }
        if(data.length() < 10) {
            return String.format("%s%s", String.join("", Collections.nCopies(data.length() / 2, "*")), data.substring(data.length() / 2));
        }
        return String.format("%s**%s", data.substring(0, 4), data.substring(data.length() - 4));
    }

    public static Map<Integer, String> codingData(Map<Integer, Value> dataMap) {
        if(dataMap == null || dataMap.size() == 0) {
            return new LinkedHashMap<>();
        }
        Map<Integer, String> result = new LinkedHashMap<>();
        for(Integer key : dataMap.keySet()) {
            Value value = dataMap.get(key);
            if(value.getValue() == null) {
                result.put(key, null);
            }else {
                result.put(key, codingData(value.toString()));
            }
        }
        return result;
    }

}
