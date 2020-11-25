package org.py.apollo.annotation;


import com.ctrip.framework.apollo.core.ConfigConsts;
import org.py.apollo.registrar.MegaApolloConfigRegistrar;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;

import java.lang.annotation.*;


/**
 * enable mega apollo config
 *
 * @author pengyue.du
 * @describe
 * 1. apollo.configuration.properties.auto.refresh.enable :
 *  auto refresh {@link ConfigurationProperties}, default false, dependence
 *  <dependency>
 *      <groupId>org.springframework.cloud</groupId>
 *      <artifactId>spring-cloud-context</artifactId>
 *      <version>${spring.boot.version}</version>
 *  </dependency>
 * 2. apollo.cache.file.expire.hours :
 *  cache file expire hours, default 0
 * 3. apollo.cache.file.expire.minutes :
 *  cache file expire minutes, default 30
 * 4. apollo.cache.file.force.sync :
 *  cache file force sync, default true
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(MegaApolloConfigRegistrar.class)
public @interface EnablePyApolloConfig {

    /**
     * Apollo namespaces to inject configuration into Spring Property Sources.
     */
    String[] value() default {ConfigConsts.NAMESPACE_APPLICATION};

    /**
     * The order of the apollo config, default is {@link Ordered#LOWEST_PRECEDENCE}, which is Integer.MAX_VALUE.
     * If there are properties with the same name in different apollo configs, the apollo config with smaller order wins.
     *
     * @return
     */
    int order() default Ordered.LOWEST_PRECEDENCE;


}
