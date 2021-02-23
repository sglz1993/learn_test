package org.py.db.starter.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.py.db.starter.mapper.api.TestMapper;
import org.py.db.starter.mapper.entity.Test;
import org.py.db.starter.service.TestService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.UUID;

/**
 * @Author pengyue.du
 * @Date 2020/8/4 11:12 上午
 * @Description
 */
@ResponseBody
//@RestController
public class TestController {

    private TestMapper testMapper;
    private TestService testService;

    public TestController(TestMapper testMapper, TestService testService) {
        this.testMapper = testMapper;
        this.testService = testService;
    }

    @GetMapping(value = {"/hello","/hello1"})
    public String hello(@RequestParam(value = "id", defaultValue="1") Integer id) {
        return JSON.toJSONString(testMapper.selectById(id));
    }

    @GetMapping(value = {"/helloCondition"})
    public String helloCondition(@RequestParam(value = "id", defaultValue="1") Integer id) {
        LambdaQueryWrapper<Test> wrapper = Wrappers.lambdaQuery(Test.class).eq(Test::getId, id).apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'");
        String sqlSelect = wrapper.getCustomSqlSegment();
        System.out.println(sqlSelect);
        return JSON.toJSONString(testMapper.selectList(Wrappers.lambdaQuery(Test.class).eq(Test::getId, id)));
    }


    @GetMapping(value = "/insert")
    public String insert(@RequestParam(value = "name", required = false) String name) {
        if(StringUtils.isEmpty(name)){
            name = UUID.randomUUID().toString().replace("-", "");
        }
//        new Test(name).updateById();
        Test test = new Test(name);
        int insert = testMapper.insert(test);
        return JSON.toJSONString(test);
    }

    @GetMapping(value = "/transaction")
    public String insert(boolean success) {
        if (success) {
            testService.transactionSuccess();
            return "OK";
        }else {
            try {
                testService.transactionError();
            } catch (Exception e) {
            }
            return "FAILURE";
        }
    }


}
