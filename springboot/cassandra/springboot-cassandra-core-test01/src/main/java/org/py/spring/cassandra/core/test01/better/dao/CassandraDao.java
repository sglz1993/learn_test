package org.py.spring.cassandra.core.test01.better.dao;

import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;

import java.util.ArrayList;
import java.util.List;

public interface CassandraDao {

    <T> void upsert(Class<T> clz, T record);

    public <T> void upsert(Class<T> clz, List<T> records);

    <T> List<T> query(Class<T> clz, Statement statement);

    <T> List<T> query(Class<T> clz, String statement);

    <T> List<T> queryAsync(Class<T> clz, Statement statement);

    default <T> List<T> mapping(ResultSet resultSet, Class<T> clazz) {
        Result<T> result = getMapperManage().mapper(clazz).map(resultSet);
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

    default <T> Mapper<T> getMapper(Class<T> clz) {
        Mapper<T> mapper = getMapperManage().mapper(clz);
        mapper.setDefaultSaveOptions(Mapper.Option.saveNullFields(false));
        return mapper;
    }

    MappingManager getMapperManage();


}
