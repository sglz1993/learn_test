package org.py.apollo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * @author: pengyue.du
 * @time: 2020/7/22 5:23 下午
 */
public class SpringConfigMonitor extends ConfigMonitor{

    @Bean
    public Object autoCheckConfigCacheFile(){
        checkConfigCacheFile(getNamespaces());
        return new Object();
    }

    @Override
    @Value("#{'${apollo.bootstrap.namespaces:application}'.split(',')}")
    public void setNamespaces(List<String> namespaces) {
        super.setNamespaces(namespaces);
    }

    @Override
    @Value("${apollo.cache.file.expire.hours:0}")
    public void setExpireHours(int expireHours) {
        super.setExpireHours(expireHours);
    }

    @Override
    @Value("${apollo.cache.file.expire.minutes:30}")
    public void setExpireMinutes(int expireMinutes) {
        super.setExpireMinutes(expireMinutes);
    }

    @Override
    @Value("${apollo.cache.file.force.sync:true}")
    public void setSyncCacheFile(boolean syncCacheFile) {
        super.setSyncCacheFile(syncCacheFile);
    }
}
