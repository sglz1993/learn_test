package org.py.spring.cassandra.core.test01.dao.base;

import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import org.apache.commons.collections4.CollectionUtils;
import org.py.spring.cassandra.core.test01.config.CassandraConnector;
import org.py.spring.cassandra.core.test01.entity.Teacher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 */
public class BaseDao {

    private static final Logger logger = LoggerFactory.getLogger(BaseDao.class);

    private static final BaseDao instance = new BaseDao();

    private volatile MappingManager mappingManager = null;

    private BaseDao() {
    }

    public static BaseDao getInstance() {
        return instance;
    }

    private MappingManager getMapperManage() {
        if (null == mappingManager) {
            synchronized (BaseDao.class) {
                if (null == mappingManager) {
                    mappingManager = new MappingManager(CassandraConnector.getInstance().getSession());
                    mappingManager.udtCodec(Teacher.class);
                }
            }
        }
        return mappingManager;
    }

    public <T> void upsert(Class<T> clz, T record) {

        boolean success = true;
        long startMs = System.currentTimeMillis();
        try {
            Mapper<T> mapper = getMapper(clz);
            mapper.save(record);
        } catch (Exception e) {
            success = false;
            logger.error("Failed to upsert type: {}, after {}ms,", clz.getTypeName(), System.currentTimeMillis()-startMs, e);
//            MetricUtils.cassandraError(clz.getSimpleName(), e.getClass().getSimpleName());
        } finally {
            try {
                // 也是Prometheus监控
//                Monitor.newBuilder().addTag("table", clz.getSimpleName()).addTag("op_type", "single")
//                        .build().avgByCount(CASS_WRITE_COST, System.currentTimeMillis() - startMs);

//                Monitor.newBuilder().addTag("table", clz.getSimpleName()).addTag("result", success ?
//                        "success" : "fail").build().increase(CASS_WRITE_CNT, 1);
            } catch (Exception e) {
                logger.error("Raise and exception when record metrics to falcon! Cause : {}", e.getCause());
            }
        }
    }

    public <T> void upsert(Class<T> clz, List<T> records) {
        if (CollectionUtils.isNotEmpty(records)) {
            boolean success = true;
            long startMs = System.currentTimeMillis();
            try {
                Mapper<T> mapper = getMapper(clz);
                BatchStatement batch = new BatchStatement();
                for (T record : records) {
                    batch.add(mapper.saveQuery(record));
                }
                CassandraConnector.getInstance().getSession().execute(batch);
            } catch (InvalidQueryException e) {
                logger.error("Failed to batch upsert: {}, records size : {}", e.getMessage(), records.size());
//                MetricUtils.cassandraError(clz.getSimpleName(), e.getClass().getSimpleName());
                for (T record : records) {
                    upsert(clz, record);
                }
                return;
            } catch (Exception e) {
                success = false;
                logger.error("Failed to upsert type : " + clz.getSimpleName(), e);
//                MetricUtils.cassandraError(clz.getSimpleName(), e.getClass().getSimpleName());
            } finally {
                try {
//                    Monitor.newBuilder().addTag("table", clz.getSimpleName()).addTag("op_type", "batch")
//                            .build().avgByCount(CASS_WRITE_COST, (System.currentTimeMillis() - startMs) / records.size());
//
//                    Monitor.newBuilder().addTag("table", clz.getSimpleName()).addTag("result", success ?
//                            "success" : "fail").build().increase(CASS_WRITE_CNT, 1);
                } catch (Exception e) {
                    logger.error("Raise and exception when record metrics to falcon! Cause : {}", e.getCause());
                }
            }
        }
    }

    private <T> Mapper<T> getMapper(Class<T> clz) {
        Mapper<T> mapper = getMapperManage().mapper(clz);
        mapper.setDefaultSaveOptions(Mapper.Option.saveNullFields(false));
        return mapper;
    }
}
