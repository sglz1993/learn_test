package org.py.spring.mybatis.hello.service;

import lombok.extern.slf4j.Slf4j;
import org.py.spring.mybatis.hello.mapper.api.TestMapper;
import org.py.spring.mybatis.hello.mapper.entity.Test;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Author pengyue.du
 * @Date 2020/8/5 7:17 下午
 * @Description
 */
@Slf4j
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
        try {
            testMapper.insert(new Test("failure1"));
            int a = 10/0;
            testMapper.insert(new Test("failure2"));
        } catch (Exception e) {
            throw e;
        }
    }
}
