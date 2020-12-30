package org.py.common.util;

import com.google.gson.*;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Util {

    private static final Gson gsonIns = new GsonBuilder()
            .disableHtmlEscaping()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context)
                            -> new JsonPrimitive(src.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() / 1000))
            .registerTypeAdapter(LocalDateTime.class,
                    (JsonDeserializer<LocalDateTime>) (jsonElement, type, jdc)
                            -> jsonElement.getAsLong() == 0 ? null : LocalDateTime.ofInstant(Instant.ofEpochMilli(jsonElement.getAsLong() * 1000), ZoneId.systemDefault()))
            .create();

    public static Gson getGson() {
        return gsonIns;
    }

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
