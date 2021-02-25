package org.py.spring.mybatisplus.hello.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.AES;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zaxxer.hikari.HikariDataSource;
import org.py.spring.mybatisplus.hello.mapper.api.TestMapper;
import org.py.spring.mybatisplus.hello.mapper.entity.Test;
import org.py.spring.mybatisplus.hello.service.TestService;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

/**
 * @Author pengyue.du
 * @Date 2020/8/4 11:12 上午
 * @Description
 */
@RestController
public class TestController {

    @Resource
    private TestMapper testMapper;
    @Resource
    private TestService testService;
    @Resource
    private HikariDataSource hikariDataSource;

    @GetMapping(value = {"/hello", "/hello1"})
    public String hello(@RequestParam(value = "id", defaultValue = "1") Integer id) {
        // 生成 16 位随机 AES 密钥
        String randomKey = AES.generateRandomKey();

        // 随机密钥加密
        String result = AES.encrypt(id.toString(), randomKey);
        return JSON.toJSONString(testMapper.selectById(id));
    }

    //    https://baomidou.com/guide/page.html
    @GetMapping(value = {"/helloCondition"})
    public String helloCondition(@RequestParam(value = "id", defaultValue = "1") Integer id) {
        LambdaQueryWrapper<Test> wrapper = Wrappers.lambdaQuery(Test.class).eq(Test::getId, id).apply("date_format(dateColumn,'%Y-%m-%d') = '2008-08-08'");
        String sqlSelect = wrapper.getCustomSqlSegment();
        System.out.println(sqlSelect);
        return JSON.toJSONString(testMapper.selectList(Wrappers.lambdaQuery(Test.class).eq(Test::getId, id)));
    }

    @GetMapping(value = {"/page"})
    public String page(@RequestParam(value = "id", defaultValue = "1") Integer idx) {
        Page<Test> page = new Page<>(idx, 10, true);
        page.addOrder(OrderItem.asc("t_bool"));
        page.addOrder(OrderItem.asc("name"));
        Page<Test> testPage = testMapper.selectPage(page, Wrappers.lambdaQuery(Test.class).gt(Test::getId, 1));
        System.out.println(JSON.toJSONString(testPage, true));
        return JSON.toJSONString(testPage.getRecords());
    }


    @GetMapping(value = "/insert")
    public String insert(@RequestParam(value = "name", required = false) String name) {
        if (StringUtils.isEmpty(name)) {
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
        } else {
            try {
                testService.transactionError();
            } catch (Exception e) {
            }
            return "FAILURE";
        }
    }

    @GetMapping(value = "/jdbcBach")
    public String jdbcBach() {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            connection = hikariDataSource.getConnection();
            String sql = "insert into t(id,name) values(?,?)";
            st = (PreparedStatement) connection.prepareStatement(sql);
            st.setString(1, null);
            st.setString(2, "aa");
            st.addBatch();
            st.setString(1, null);
            st.setString(2, "bb");
            st.addBatch();
            st.executeBatch();
            st.clearBatch();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        return "OK";
    }


}
