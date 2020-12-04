package org.py.spring.cassandra.core.test01.controller;

import com.alibaba.fastjson.JSON;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import lombok.extern.slf4j.Slf4j;
import org.py.spring.cassandra.core.test01.better.dao.CassandraOption;
import org.py.spring.cassandra.core.test01.entity.Teacher;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.UUID;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

@Slf4j
@RestController
public class TestController {

    @Resource
    private CassandraOption cassandraOption;

    @RequestMapping("insert")
    public String insert(@RequestParam(required = false, defaultValue = "false") Boolean fix,
                         @RequestParam(required = false, defaultValue = "1") Integer age,
                         @RequestParam(required = false, defaultValue = "name") String name) {
        Teacher teacher = new Teacher(1, "sf", name, age, 1);
        if(!fix) {
            teacher = new Teacher((int) (System.currentTimeMillis()%100), UUID.randomUUID().toString(), "lal", 1, 1);
        }
        cassandraOption.upsert(Teacher.class, teacher);
        return JSON.toJSONString(teacher);
    }

    @RequestMapping("all")
    public String all() {
        Statement statement = new Select.SelectionOrAlias().all().from("teacher").setFetchSize(100);
        List<Teacher> teachers = cassandraOption.query(Teacher.class, statement);
        return JSON.toJSONString(teachers);
    }

    @RequestMapping("byId")
    public String byId() {
        Statement statement = QueryBuilder.select().all().from("teacher").where(eq("id", 1)).setFetchSize(100);
        List<Teacher> teachers = cassandraOption.queryAsync(Teacher.class, statement);
        return JSON.toJSONString(teachers);
    }


}
