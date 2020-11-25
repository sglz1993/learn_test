package org.py.apollo.config;

import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.List;

/**
 * @author: pengyue.du
 * @time: 2020/7/22 5:39 下午
 */
public class SpringConfig extends Config implements InitializingBean, ApplicationContextAware {

    private boolean configurationPropertiesAutoRefreshEnable = false;

    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        init();
    }


    @Override
    @Value("#{'${apollo.bootstrap.namespaces:application}'.split(',')}")
    public void setConfigNamespace(List<String> namespaces) {
        super.setConfigNamespace(namespaces);
    }

    @Value("${apollo.configuration.properties.auto.refresh.enable:false}")
    public void setConfigurationPropertiesAutoRefreshEnable(boolean configurationPropertiesAutoRefreshEnable) {
        this.configurationPropertiesAutoRefreshEnable = configurationPropertiesAutoRefreshEnable;
    }

    @Override
    protected ConfigChangeListener getChangeListener() {
        if(configurationPropertiesAutoRefreshEnable) {
            return new PerferChangeListenerExector(applicationContext);
        }else {
            return super.getChangeListener();
        }
    }

    static class PerferChangeListenerExector extends ChangeListenerExector implements ConfigChangeListener {

        private ApplicationContext applicationContext;

        public PerferChangeListenerExector(ApplicationContext applicationContext) {
            this.applicationContext = applicationContext;
        }

        @Override
        public void onChange(ConfigChangeEvent changeEvent) {
            this.applicationContext.publishEvent(new EnvironmentChangeEvent(changeEvent.changedKeys()));
            super.onChange(changeEvent);
        }
    }
}
