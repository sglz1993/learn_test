package org.py.p6spy.client.plugs;

import com.mysql.cj.jdbc.JdbcConnection;
import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.Loggable;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.Value;
import com.p6spy.engine.logging.Category;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.py.p6spy.client.entry.SQLDetail;
import org.py.p6spy.client.producer.SQLRecordProducer;
import org.py.p6spy.client.sample.DefaultSamplingStrategy;
import org.py.p6spy.client.util.ReflectUtils;
import org.py.p6spy.client.util.Util;

import java.sql.SQLException;
import java.time.Instant;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Author pengyue.du
 * @Date 2020/9/10 5:23 pm
 * @Description
 */
@Slf4j
public class SQLRecorder {

    private static final DefaultSamplingStrategy SAMPLING_STRATEGY = new DefaultSamplingStrategy();

    /**
     * 哪个服务，哪个库，读/写，原始SQL，执行时间，哪个用户操作的，哪个pod调用，调用时间
     * @param loggable
     * @param timeElapsedNanos  耗时
     * @param category
     * @param e
     */
    public static void logElapsed(Loggable loggable, long timeElapsedNanos, Category category, SQLException e) {
        if(!SQLAnalyseConfig.init) {
            log.warn("config not init , please config {}", SQLRecorder.class.getName());
            return;
        }
        try {
            ConnectionInformation connectionInformation = loggable.getConnectionInformation();
            String sql = loggable.getSql();
            if(StringUtils.isNotBlank(sql)) {
                sql = sql.trim();
                if (!SQLAnalyseConfig.initSQL.contains(sql)) {
                    Map<Integer, Value> parameterValues = null;
                    if (loggable instanceof PreparedStatementInformation) {
                        parameterValues = (Map<Integer, Value>) ReflectUtils.invoke(PreparedStatementInformation.class, "getParameterValues", loggable);
                    }
                    JdbcConnection connection = (JdbcConnection) connectionInformation.getConnection();
                    String database = connection.getDatabase();
                    String user = connection.getUser();
                    SQLDetail sqlDetail = new SQLDetail(SQLAnalyseConfig.appId, SQLAnalyseConfig.serviceName, database,
                            OperationParser.parse(sql).name(), sql, Util.codingData(parameterValues), TimeUnit.NANOSECONDS.toMillis(timeElapsedNanos),
                            user, Util.getHostName(), Instant.now().toEpochMilli(), category.getName());
                    if (SAMPLING_STRATEGY.checkSimple(sqlDetail)) {
                        SQLRecordProducer.sendSQLRecord(sqlDetail);
                    }else {
                        log.debug("sampling continue, current sql record info:{}", Util.getGson().toJson(sqlDetail));
                    }
                }else {
                    log.debug("init sql:{} , continue ", sql);
                }
            }else {
                log.debug("sql is empty, continue");
            }
        } catch (SQLException throwables) {
            log.error("parse sql error", throwables);
        }
    }


}
