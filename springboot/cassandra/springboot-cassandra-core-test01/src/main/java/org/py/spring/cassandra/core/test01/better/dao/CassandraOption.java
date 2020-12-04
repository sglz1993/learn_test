package org.py.spring.cassandra.core.test01.better.dao;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class CassandraOption implements CassandraDao{

    @Resource
    private Session session;
    @Resource
    private MappingManager mappingManager;


    @Override
    public <T> void upsert(Class<T> clz, T record) {
        Mapper<T> mapper = getMapper(clz);
        mapper.save(record);
    }

    @Override
    public <T> void upsert(Class<T> clz, List<T> records) {
        if(CollectionUtils.isNotEmpty(records)) {
            Mapper<T> mapper = getMapper(clz);
            BatchStatement batch = new BatchStatement();
            for (T record : records) {
                batch.add(mapper.saveQuery(record));
            }
            session.execute(batch);
        }
    }

    @Override
    public <T> List<T> query(Class<T> clz, Statement statement) {
        ResultSet resultSet = session.execute(statement);
        return mapping(resultSet, clz);
    }

    @Override
    public <T> List<T> query(Class<T> clz, String statement) {
        ResultSet resultSet = session.execute(statement);
        return mapping(resultSet, clz);
    }

    @Override
    public <T> List<T> queryAsync(Class<T> clz, Statement statement) {
        ResultSetFuture executeAsync = session.executeAsync(statement);
        try {
            return mapping(executeAsync.get(), clz);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MappingManager getMapperManage() {
        return mappingManager;
    }
}
