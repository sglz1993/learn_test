package org.py.apollo.logutl;

import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.core.ConfigConsts;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author pengyue.du
 * @Date 2020/9/3 2:11 pm
 * @Description
 */
public abstract class BaseConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BaseConfiguration.class);

    protected List<String> NAMESPACES = Lists.newArrayList(ConfigConsts.NAMESPACE_APPLICATION);

    protected static final String LOGGER_TAG = "logging.level.";

    public void setConfigNamespace(List<String> namespaces) {
        NAMESPACES = namespaces;
    }

    public BaseConfiguration() {
    }

    public BaseConfiguration(List<String> namespaces) {
        this.NAMESPACES = namespaces;
        addChangeListener(namespaces);
    }

    protected void addChangeListener(List<String> namespaces) {
        for (String namespace : namespaces) {
            com.ctrip.framework.apollo.Config config = ConfigService.getConfig(namespace);
            config.addChangeListener(changeEvent -> {
                try {
                    changeLogLevelOnChange(namespace);
                } catch (Throwable e) {
                    logger.error("change log level error :{}", e.getMessage(), e);
                }
            });
        }
    }

    protected static boolean containsIgnoreCase(String str, String searchStr) {
        if (str == null || searchStr == null) {
            return false;
        }
        int len = searchStr.length();
        int max = str.length() - len;
        for (int i = 0; i <= max; i++) {
            if (str.regionMatches(true, i, searchStr, 0, len)) {
                return true;
            }
        }
        return false;
    }

    protected abstract void changeLogLevelOnChange(String namespace);



}
