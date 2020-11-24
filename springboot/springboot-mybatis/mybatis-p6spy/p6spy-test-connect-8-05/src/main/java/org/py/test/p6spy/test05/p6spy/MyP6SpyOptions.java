package org.py.test.p6spy.test05.p6spy;

import com.p6spy.engine.spy.P6LoadableOptions;
import com.p6spy.engine.spy.option.P6OptionsRepository;

import javax.management.StandardMBean;
import java.util.Map;

/**
 * @Author pengyue.du
 * @Date 2020/9/10 4:13 下午
 * @Description
 */
public class MyP6SpyOptions extends StandardMBean implements P6LoadableOptions {


    public MyP6SpyOptions(final P6OptionsRepository optionsRepository) {
        super(P6LoadableOptions.class, false);
    }


    @Override
    public void load(Map<String, String> options) {

    }

    @Override
    public Map<String, String> getDefaults() {
        return null;
    }
}
