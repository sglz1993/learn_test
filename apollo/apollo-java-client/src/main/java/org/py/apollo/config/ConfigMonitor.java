package org.py.apollo.config;

import com.ctrip.framework.apollo.build.ApolloInjector;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.ctrip.framework.apollo.core.utils.ApolloThreadFactory;
import com.ctrip.framework.apollo.core.utils.ClassLoaderUtil;
import com.ctrip.framework.apollo.exceptions.ApolloConfigException;
import com.ctrip.framework.apollo.exceptions.ApolloConfigStatusCodeException;
import com.ctrip.framework.apollo.internals.RemoteConfigRepository;
import com.ctrip.framework.apollo.tracer.Tracer;
import com.ctrip.framework.apollo.tracer.spi.Transaction;
import com.ctrip.framework.apollo.util.ConfigUtil;
import com.ctrip.framework.apollo.util.ExceptionUtil;
import com.google.common.base.Joiner;
import org.joda.time.DateTime;
import org.py.apollo.util.MetricsUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;

/**
 * monitor apollo server
 *
 * @author: pengyue.du
 * @time: 2020/7/13 7:05 pm
 */
public class ConfigMonitor {

    private static final Logger log = LoggerFactory.getLogger(ConfigMonitor.class);

    private static final String CONFIG_DIR = "/config-cache";

    private final static ScheduledExecutorService m_executorService;

    static {
        m_executorService = new ScheduledThreadPoolExecutor(1,
                ApolloThreadFactory.create("RemoteConfigMonitor", true));
    }

    private List<String> namespaces;

    /**
     * cache file expire hours
     */
    private int expireHours = SystemConfig.EXPIRE_HOURS;

    /**
     * cache file expire minutes
     */
    private int expireMinutes = SystemConfig.EXPIRE_MINUTES;

    /**
     * enable force refresh cache file
     */
    private boolean syncCacheFile = SystemConfig.SYNC_CACHE_FILE;

    private ConfigUtil configUtil = ApolloInjector.getInstance(ConfigUtil.class);

    private static final Map<String, RemoteMonitor> monitorMap = new HashMap<>();

    private static volatile boolean taskInitializeFinish = false;

    public ConfigMonitor() {
    }

    public ConfigMonitor(List<String> namespaces, int expireHours, int expireMinutes, boolean syncCacheFile) {
        this.namespaces = namespaces;
        this.expireHours = expireHours;
        this.expireMinutes = expireMinutes;
        this.syncCacheFile = syncCacheFile;
        checkConfigCacheFile(namespaces);
    }

    private static File findLocalCacheDir(ConfigUtil configUtil) {
        try {
            String defaultCacheDir = configUtil.getDefaultLocalCacheDir();
            Path path = Paths.get(defaultCacheDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            if (Files.exists(path) && Files.isWritable(path)) {
                return new File(defaultCacheDir, CONFIG_DIR);
            }
        } catch (Throwable ex) {

        }

        return new File(ClassLoaderUtil.getClassPath(), CONFIG_DIR);
    }

    /**
     * check local cache file expire and monitor apollo server
     * @param namespaces
     */
    public void checkConfigCacheFile(List<String> namespaces) {
        File file = findLocalCacheDir(configUtil);
        if (file.exists() && file.isDirectory()) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            File[] files = file.listFiles();
            if (files.length > 0) {
                for (File curFile : files) {
                    if (needCheckCacheFile(namespaces, curFile.getName())) {
                        DateTime effectiveTime = new DateTime(curFile.lastModified());
                        if (expireHours != 0) {
                            effectiveTime = effectiveTime.plusHours(expireHours);
                        }
                        if (expireMinutes != 0) {
                            effectiveTime = effectiveTime.plusMinutes(expireMinutes);
                        }
                        if (effectiveTime.compareTo(new DateTime()) < 0) {
                            log.error("apollo config cache file too old ,fileName:{}", curFile.getAbsolutePath());
                            MetricsUtil.recordApolloCacheFileExpire("file expire");
                            throw new ApolloConfigStatusCodeException(2, "config file time out ");
                        }
                    }
                }
                for(String namespace : namespaces) {
                    if(!containConfig(namespace, files)) {
                        log.error("apollo config cache file miss for {}", namespace);
                        throw new ApolloConfigStatusCodeException(3, "config file miss");
                    }
                }
                log.info("Schedule periodic refresh with interval: {} {}",
                        configUtil.getRefreshInterval(), configUtil.getRefreshIntervalTimeUnit());
                synchronized (ConfigMonitor.class) {
                    if (!taskInitializeFinish) {
                        m_executorService.scheduleAtFixedRate(
                                () -> monitorConnect(), configUtil.getRefreshInterval(), configUtil.getRefreshInterval(),
                                configUtil.getRefreshIntervalTimeUnit());
                        taskInitializeFinish = true;
                    }
                }
            } else {
                log.error("apollo config cache file is empty ,fileName:{}", file.getAbsolutePath());
                MetricsUtil.recordApolloCacheFileExpire("directory empty");
                throw new ApolloConfigStatusCodeException(1, "config file is empty ");
            }
        } else {
            log.error("apollo config cache file is miss ");
            MetricsUtil.recordApolloCacheFileExpire("apollo config cache file is miss");
            throw new ApolloConfigStatusCodeException(1, "apollo config cache file is miss");
        }
        log.info("check cache file pass");
    }

    private boolean containConfig(String namespace, File[] files) {
        for(File file : files) {
            if(file.getName().endsWith(String.format("+%s.properties", namespace))) {
                return true;
            }
        }
        return false;
    }

    private boolean needCheckCacheFile(List<String> namespaces, String name) {
        for(String namespace : namespaces) {
            if(name.endsWith(String.format("+%s.properties", namespace))) {
                return true;
            }
        }
        return false;
    }

    private static synchronized RemoteMonitor getMonitor(String namespace, ConfigUtil configUtil, boolean syncCacheFile) {
        RemoteMonitor remoteMonitor = monitorMap.get(namespace);
        if(remoteMonitor == null) {
            remoteMonitor = new RemoteMonitor(namespace, configUtil, syncCacheFile);
            monitorMap.put(namespace, remoteMonitor);
        }
        return remoteMonitor;
    }

    private void monitorConnect() {
        for (String namespace : namespaces) {
            try {
                log.info("start refresh config for namespace: {}", namespace);
                getMonitor(namespace, configUtil, syncCacheFile).connectAndSync();
                log.info("success refresh config for namespace: {}", namespace);
            } catch (Exception exception) {
                log.error("apollo config server connect timeout ,namespace : {}", namespace, exception);
                MetricsUtil.recordApolloConnectTimeout("apollo config server connect timeout");
            }
        }
    }

    static class RemoteMonitor extends RemoteConfigRepository {

        private ConfigUtil configUtil;
        private String namespace;
        private boolean syncCacheFile;

        public RemoteMonitor(String namespace, ConfigUtil configUtil, boolean syncCacheFile) {
            super(namespace);
            this.configUtil = configUtil;
            this.namespace = namespace;
            this.syncCacheFile = syncCacheFile;
        }

        void connectAndSync() {
            sync();
            if (syncCacheFile) {
                persistLocalCacheFile(getConfig(), namespace);
            }
        }

        File assembleLocalCacheFile(String namespace) {
            File baseDir = findLocalCacheDir(configUtil);
            String fileName =
                    String.format("%s.properties", Joiner.on(ConfigConsts.CLUSTER_NAMESPACE_SEPARATOR)
                            .join(configUtil.getAppId(), configUtil.getCluster(), namespace));
            return new File(baseDir, fileName);
        }

        synchronized void persistLocalCacheFile(Properties properties, String namespace) {

            File file = assembleLocalCacheFile(namespace);

            Transaction transaction = Tracer.newTransaction("Apollo.ConfigService", "persistLocalConfigFile");
            transaction.addData("LocalConfigFile", file.getAbsolutePath());
            try (OutputStream out = new FileOutputStream(file)) {
                properties.store(out, "Persisted by DefaultConfig");
                transaction.setStatus(Transaction.SUCCESS);
            } catch (IOException ex) {
                ApolloConfigException exception =
                        new ApolloConfigException(
                                String.format("Persist local cache file %s failed", file.getAbsolutePath()), ex);
                Tracer.logError(exception);
                transaction.setStatus(exception);
                log.warn("Persist local cache file {} failed, reason: {}.", file.getAbsolutePath(),
                        ExceptionUtil.getDetailMessage(ex));
            } finally {
                transaction.complete();
            }
        }

    }

    public List<String> getNamespaces() {
        return namespaces;
    }

    public void setNamespaces(List<String> namespaces) {
        this.namespaces = namespaces;
    }

    public int getExpireHours() {
        return expireHours;
    }

    public void setExpireHours(int expireHours) {
        this.expireHours = expireHours;
    }

    public int getExpireMinutes() {
        return expireMinutes;
    }

    public void setExpireMinutes(int expireMinutes) {
        this.expireMinutes = expireMinutes;
    }

    public boolean isSyncCacheFile() {
        return syncCacheFile;
    }

    public void setSyncCacheFile(boolean syncCacheFile) {
        this.syncCacheFile = syncCacheFile;
    }
}
