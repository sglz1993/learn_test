package org.py.apollo.logutl;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;
import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

/**
 * @Author pengyue.du
 * @Date 2020/9/3 2:18 pm
 * @Description
 */
public class LogbackConfiguration extends BaseConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(LogbackConfiguration.class);

    public LogbackConfiguration(List<String> namespaces) {
        super(namespaces);
        refreshLogLevel();
    }

    public void refreshLogLevel() {
        for (String namespace : NAMESPACES) {
            changeLogLevelOnChange(namespace);
        }
    }

    @Override
    protected void changeLogLevelOnChange(String namespace) {
        Config config = ConfigService.getConfig(namespace);
        Set<String> keyNames = config.getPropertyNames();
        for (String key : keyNames) {
            if (containsIgnoreCase(key, LOGGER_TAG)) {
                String strLevel = config.getProperty(key, "debug");
                setLogLevel(key.replace(LOGGER_TAG, ""), strLevel);
                logger.info("{}:{}", key, strLevel);
            }
        }
    }

    protected void setLogLevel(String name, String level) {
        LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
        ch.qos.logback.classic.Logger logger = loggerContext.getLogger(name);
        if (logger != null) {
            logger.setLevel(Level.toLevel(level));
        }
    }
}
