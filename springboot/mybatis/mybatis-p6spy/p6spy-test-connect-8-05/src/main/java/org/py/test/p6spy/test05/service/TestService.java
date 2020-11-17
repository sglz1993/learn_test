package org.py.test.p6spy.test05.service;

import org.py.test.p6spy.test05.mapper.api.TestMapper;
import org.py.test.p6spy.test05.mapper.entity.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author pengyue.du
 * @Date 2020/8/5 7:17 下午
 * @Description
 */
@Service
public class TestService {

    @Resource
    private TestMapper testMapper;

    @Transactional(rollbackFor = Throwable.class)
    public void transactionSuccess() {
        testMapper.insert(new Test("success1"));
        testMapper.insert(new Test("success2"));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void transactionError() {
        testMapper.insert(new Test("failure1"));
        int a = 10/0;
        testMapper.insert(new Test("failure2"));
    }
}
