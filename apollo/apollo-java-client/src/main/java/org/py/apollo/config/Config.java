package org.py.apollo.config;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.core.utils.StringUtils;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * provide basic configuration query and monitoring
 *
 * @author: pengyue.du
 * @time: 2020/6/17 10:18 am
 */
public class Config {

    protected static final Logger log = LoggerFactory.getLogger(Config.class);
    private static final Map<String, TreeSet<OrderListener>> ORDER_LISTENERS_MAP = new HashMap<>();
    private static List<String> NAMESPACES = Lists.newArrayList(ConfigConsts.NAMESPACE_APPLICATION);

    public Config() {
    }

    public Config(List<String> namespaces) {
        if(CollectionUtils.isEmpty(namespaces)){
            throw new RuntimeException("NAMESPACES can't empty");
        }
        this.setConfigNamespace(namespaces);
        init();
    }

    public static String getConfig(String key) {
        return getConfig(key, null);
    }

    public static Integer getIntConfig(String key) {
        String config = getConfig(key, "");
        if (!StringUtils.isBlank(config)) {
            return Integer.valueOf(config);
        }
        return null;
    }

    public static Long getLongConfig(String key) {
        String config = getConfig(key, "");
        if (!StringUtils.isBlank(config)) {
            return Long.valueOf(config);
        }
        return null;
    }

    public static List<String> getListConfig(String key, String delimiter) {
        String config = getConfig(key, "");
        if (!StringUtils.isBlank(config)) {
            return Lists.newArrayList(config.split(delimiter));
        }
        return Lists.newArrayList();
    }

    public static String getConfig(String key, String defaultValue) {
        for (String namespace : NAMESPACES) {
            com.ctrip.framework.apollo.Config config = ConfigService.getConfig(namespace);
            String property = config.getProperty(key, null);
            if (property != null) {
                return property;
            }
        }
        return defaultValue;
    }

    public static Set<String> getKeysByPrefix(String prefix) {
        Set<String> result = new HashSet<>();
        for (String namespace : NAMESPACES) {
            com.ctrip.framework.apollo.Config config = ConfigService.getConfig(namespace);
            Set<String> propertyNames = config.getPropertyNames();
            Set<String> filterKeys = propertyNames.stream().filter(s -> s.equals(prefix) || s.startsWith(prefix + ".")).collect(Collectors.toSet());
            if (CollectionUtils.isNotEmpty(filterKeys)) {
                result.addAll(filterKeys);
            }
        }
        return result;
    }

    /**
     * add listener
     *
     * @param keyParttern
     * @param listener
     * @param order       order the smaller, the higher the priority
     */
    public static synchronized void addListener(String keyParttern, SingleKeyConfigChangeListener listener, int order) {
        TreeSet<OrderListener> orderListeners = ORDER_LISTENERS_MAP.get(keyParttern);
        if (orderListeners == null) {
            orderListeners = new TreeSet<>();
        }
        orderListeners.add(new OrderListener(listener, order));
        ORDER_LISTENERS_MAP.put(keyParttern, orderListeners);
    }

    public void init() {
        for (String namespace : NAMESPACES) {
            com.ctrip.framework.apollo.Config config = ConfigService.getConfig(namespace);
            config.addChangeListener(getChangeListener());
        }
    }

    protected ConfigChangeListener getChangeListener() {
        return new ChangeListenerExector();
    }

    public void setConfigNamespace(List<String> namespaces) {
        NAMESPACES = namespaces;
    }

    public interface SingleKeyConfigChangeListener {

        void onChange(ConfigChangeMessage changeMessage);

    }

    public static class ConfigChangeMessage {

        private String key;

        private String namespace;

        private ConfigChange configChange;

        public ConfigChangeMessage(String key, String namespace, ConfigChange configChange) {
            this.key = key;
            this.namespace = namespace;
            this.configChange = configChange;
        }

        public String getKey() {
            return key;
        }

        public String getNamespace() {
            return namespace;
        }

        public ConfigChange getConfigChange() {
            return configChange;
        }
    }

    static class ChangeListenerExector implements ConfigChangeListener {

        public ChangeListenerExector() {
        }

        @Override
        public void onChange(ConfigChangeEvent changeEvent) {
            for (String key : changeEvent.changedKeys()) {
                ConfigChange change = changeEvent.getChange(key);
                log.info("Change - key: {}, oldValue: {}, newValue: {}, changeType: {}, namespace: {}",
                        change.getPropertyName(), change.getOldValue(), change.getNewValue(),
                        change.getChangeType(), change.getNamespace());
                Set<String> keyPatterns = ORDER_LISTENERS_MAP.keySet().stream().filter(keyPattern -> key.matches(key)).collect(Collectors.toSet());
                List<OrderListener> orderListeners = new LinkedList<>();
                keyPatterns.stream().forEach(keyPattern -> orderListeners.addAll(ORDER_LISTENERS_MAP.get(keyPattern)));
                Iterator<OrderListener> iterator = orderListeners.iterator();
                while (iterator.hasNext()) {
                    iterator.next().onChange(key, changeEvent.getNamespace(), change);
                }
            }
        }
    }

    protected static class OrderListener implements Comparable<OrderListener> {

        private SingleKeyConfigChangeListener listener;

        private int order;

        public OrderListener(SingleKeyConfigChangeListener listener, int order) {
            this.listener = listener;
            this.order = order;
        }

        public void onChange(String key, String namespace, ConfigChange configChang) {
            listener.onChange(new ConfigChangeMessage(key, namespace, configChang));
        }

        public int getOrder() {
            return order;
        }

        @Override
        public int compareTo(OrderListener o) {
            return order - o.getOrder();
        }
    }
}
