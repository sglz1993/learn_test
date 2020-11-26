package org.py.p6spy.client.plugs;

import com.p6spy.engine.event.JdbcEventListener;
import com.p6spy.engine.spy.P6Factory;
import com.p6spy.engine.spy.P6LoadableOptions;
import com.p6spy.engine.spy.option.P6OptionsRepository;

/**
 * @Author pengyue.du
 * @Date 2020/9/10 4:10 下午
 * @Description
 */
public class MyP6Factory implements P6Factory {

    @Override
    public P6LoadableOptions getOptions(P6OptionsRepository optionsRepository) {
        return new MyP6SpyOptions(optionsRepository);
    }

    @Override
    public JdbcEventListener getJdbcEventListener() {
        return MyJdbcEventListener.INSTANCE;
    }

}
