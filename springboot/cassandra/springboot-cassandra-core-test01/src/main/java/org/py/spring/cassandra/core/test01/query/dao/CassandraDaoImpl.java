package org.py.spring.cassandra.core.test01.query.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import org.py.spring.cassandra.core.test01.config.CassandraConnector;
import org.py.spring.cassandra.core.test01.entity.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

//@Component
public class CassandraDaoImpl implements CassandraDao, InitializingBean {

    private static final Logger logger = LoggerFactory.getLogger(CassandraDaoImpl.class);

    @Resource
    private CassandraConnector cassandraConnector;

    private MappingManager mappingManager;

    @Override
    public void afterPropertiesSet() {
        if (cassandraConnector.getSession() == null) {
            return;
        }
        this.mappingManager = new MappingManager(cassandraConnector.getSession());
        mappingManager.udtCodec(Teacher.class);
    }

    @Override
    public ResultSet execute(Statement statement) {
        logger.info("query statement: [{}]", statement.toString());
        return cassandraConnector.getSession().execute(statement);
    }

    @Override
    public ResultSet execute(String statement) {
        logger.info("query statement: [{}]", statement);
        return cassandraConnector.getSession().execute(statement);
    }

    @Override
    public ResultSetFuture executeAsync(Statement statement) {
        logger.info("query statement: [{}]", statement);
        return cassandraConnector.getSession().executeAsync(statement);
    }

    @Override
    public <T> List<T> mapping(ResultSet resultSet, Class<T> clazz) {
        Result<T> result = mappingManager.mapper(clazz).map(resultSet);
        List<T> resultList = new ArrayList<>();
        int remaining = result.getAvailableWithoutFetching();
        for (T t : result) {
            resultList.add(t);
            if (--remaining <= 0) {
                break;
            }
        }
        return resultList;
    }
}
