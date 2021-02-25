package org.py.spring.mybatis.hello.controller;

import com.alibaba.fastjson.JSON;
import com.zaxxer.hikari.HikariDataSource;
import org.py.spring.mybatis.hello.mapper.api.TestMapper;
import org.py.spring.mybatis.hello.mapper.entity.Test;
import org.py.spring.mybatis.hello.service.TestService;
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
//    @Resource
//    private org.py.db.starter.service.TestService simpleTestService;

    @GetMapping(value = {"/hello","/hello1"})
    public String hello(@RequestParam(value = "id", defaultValue="1") Integer id) {
//        simpleTestService.hello();
        return JSON.toJSONString(testMapper.selectByPrimaryKey(id));
    }


    @GetMapping(value = "/insert")
    public String insert(@RequestParam(value = "name", required = false) String name) {
        if(StringUtils.isEmpty(name)){
            name = UUID.randomUUID().toString().replace("-", "");
        }
        return JSON.toJSONString(testMapper.insert(new Test(name)));
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

    @GetMapping(value = "/jdbcBach")
    public String jdbcBach() {
        Connection connection = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            connection = hikariDataSource.getConnection();
            String sql = "insert into t(id,name) values(?,?)";
            st =(PreparedStatement) connection.prepareStatement(sql);
            st.setString(1,null);
            st.setString(2,"aa");
            st.addBatch();
            st.setString(1,null);
            st.setString(2,"bb");
            st.addBatch();
            st.executeBatch();
            st.clearBatch();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
        }
        return "OK";
    }



}
