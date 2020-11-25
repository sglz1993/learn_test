package org.py.test.p6spy.test05.service;

import org.py.test.p6spy.test05.mapper.api.TestMapper;
import org.py.test.p6spy.test05.mapper.entity.TestEntry;
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
        testMapper.insert(new TestEntry("success1"));
        testMapper.insert(new TestEntry("success2"));
    }

    @Transactional(rollbackFor = Throwable.class)
    public void transactionError() {
        testMapper.insert(new TestEntry("failure1"));
        int a = 10/0;
        testMapper.insert(new TestEntry("failure2"));
    }
}
