package org.py.spring.cassandra.core.test01.query.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.ResultSetFuture;
import com.datastax.driver.core.Statement;

import java.util.List;


public interface CassandraDao {
    ResultSet execute(Statement statement);

    ResultSet execute(String statement);

    ResultSetFuture executeAsync(Statement statement);

    <T> List<T> mapping(ResultSet resultSet, Class<T> clazz);
}
