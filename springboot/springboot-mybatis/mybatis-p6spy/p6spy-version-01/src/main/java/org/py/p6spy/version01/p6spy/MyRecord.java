package org.py.p6spy.version01.p6spy;

import com.mysql.cj.jdbc.JdbcConnection;
import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.Loggable;
import com.p6spy.engine.common.PreparedStatementInformation;
import com.p6spy.engine.common.Value;
import com.p6spy.engine.logging.Category;
import lombok.extern.slf4j.Slf4j;
import org.py.common.reflect.ReflectUtils;
import org.py.common.thread.StackUtil;

import javax.sql.CommonDataSource;
import javax.sql.PooledConnection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

/**
 * @Author pengyue.du
 * @Date 2020/9/10 5:23 下午
 * @Description
 */
@Slf4j
public class MyRecord {

    /**
     *
     * @param loggable
     * @param timeElapsedNanos  耗时
     * @param category
     * @param e
     */
    public static void logElapsed(Loggable loggable, long timeElapsedNanos, Category category, SQLException e) {
        int connectionId = 0; // connectionId
        String sql = null; // 原始SQL
        try {
            StackUtil.printThreadStack(5);
            ConnectionInformation connectionInformation = loggable.getConnectionInformation(); // ConnectionInformation
            String sql1 = connectionInformation.getSql(); // ""
            String url = connectionInformation.getUrl(); // url
            connectionId = connectionInformation.getConnectionId();
            CommonDataSource dataSource = connectionInformation.getDataSource(); // null
            sql = loggable.getSql();
            String name = category.getName(); //Category
            Map<Integer, Value> parameterValues = null;
            if (loggable instanceof PreparedStatementInformation) {
                parameterValues = (Map<Integer, Value>) ReflectUtils.invoke(PreparedStatementInformation.class, "getParameterValues", loggable);
            }
            String sqlWithValues = loggable.getSqlWithValues(); //替换值后的SQL
            JdbcConnection connection = (JdbcConnection) connectionInformation.getConnection(); //数据库，用户名、密码等
            Properties properties = connection.getProperties();
            String database = connection.getDatabase();
            String user = connection.getUser();
            long id = connection.getId();
            Driver driver = connectionInformation.getDriver(); //非null 但是暂时未发现有效内容
            String sqlWithValues1 = connectionInformation.getSqlWithValues(); // ""
            PooledConnection pooledConnection = connectionInformation.getPooledConnection(); //null


            ConnectionInformation connectionInformation1 = connectionInformation.getConnectionInformation();
            boolean c1 = connectionInformation == connectionInformation1; //true
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        log.info("category : {}, cid : {}, sql : {}, timeElapsedNanos: {}", category.getName(), connectionId, sql, TimeUnit.NANOSECONDS.toMillis(timeElapsedNanos));
    }


}
