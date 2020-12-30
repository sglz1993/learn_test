package org.py.test.p6spy.test03.p6spy;

import com.mysql.cj.api.jdbc.JdbcConnection;
import com.mysql.cj.core.conf.url.ConnectionUrlParser;
import com.mysql.cj.jdbc.DatabaseMetaData;
import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.Loggable;
import com.p6spy.engine.logging.Category;
import lombok.extern.slf4j.Slf4j;

import javax.sql.CommonDataSource;
import javax.sql.PooledConnection;
import java.sql.Driver;
import java.sql.SQLException;
import java.util.Properties;

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
        ConnectionInformation connectionInformation = loggable.getConnectionInformation(); // ConnectionInformation
        String sql1 = connectionInformation.getSql(); // ""
        String url = connectionInformation.getUrl(); // url
        int connectionId = connectionInformation.getConnectionId(); // connectionId
        CommonDataSource dataSource = connectionInformation.getDataSource(); // null
        String sql = loggable.getSql(); // 原始SQL
        String name = category.getName(); //Category
        String sqlWithValues = loggable.getSqlWithValues(); //替换值后的SQL
        JdbcConnection connection = (JdbcConnection) connectionInformation.getConnection(); //数据库，用户名、密码等
        Properties properties = connection.getProperties();
        DatabaseMetaData metaData = null;
        try {
            String url1 = connection.getURL();
            String path = ConnectionUrlParser.parseConnectionString(connection.getURL()).getPath();
            metaData = (DatabaseMetaData) connection.getMetaData();
            String databaseProductName = metaData.getUserName();
            String driverName = metaData.getDriverName();
            String userName = metaData.getUserName();
            String user = connection.getUser();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        Driver driver = connectionInformation.getDriver(); //非null 但是暂时未发现有效内容
        String sqlWithValues1 = connectionInformation.getSqlWithValues(); // ""
        PooledConnection pooledConnection = connectionInformation.getPooledConnection(); //null


        ConnectionInformation connectionInformation1 = connectionInformation.getConnectionInformation();
        boolean c1 = connectionInformation == connectionInformation1; //true

        log.info("category : {}, cid : {}, sql : {}", category.getName(), connectionId, sql);
    }

}
