package org.py.apollo.logutl;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Set;

/**
 * @author pengyue.du
 * @Date 2020/9/3 2:18 pm
 * @Description
 */
@Component
public class LoggerConfiguration extends BaseConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(LoggerConfiguration.class);

  @Autowired
  private LoggingSystem loggingSystem;


  @PostConstruct
  private void refreshLoggingLevels() {
    for (String namespace : NAMESPACES) {
      changeLogLevelOnChange(namespace);
    }
    addChangeListener(NAMESPACES);
  }

  @Override
  @Value("#{'${apollo.bootstrap.namespaces:application}'.split(',')}")
  public void setConfigNamespace(List<String> namespaces) {
    super.setConfigNamespace(namespaces);
  }

  @Override
  protected void changeLogLevelOnChange(String namespace) {
    Config config = ConfigService.getConfig(namespace);
    Set<String> keyNames = config.getPropertyNames();
    for (String key : keyNames) {
      if (containsIgnoreCase(key, LOGGER_TAG)) {
        String strLevel = config.getProperty(key, "debug");
        LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
        loggingSystem.setLogLevel(key.replace(LOGGER_TAG, ""), level);
        logger.info("{}:{}", key, strLevel);
      }
    }
  }

}
