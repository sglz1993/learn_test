package org.py.test.p6spy.test02.p6spy;

import com.mysql.cj.MysqlConnection;
import com.p6spy.engine.common.ConnectionInformation;
import com.p6spy.engine.common.Loggable;
import com.p6spy.engine.logging.Category;
import lombok.extern.slf4j.Slf4j;

import javax.sql.CommonDataSource;
import javax.sql.PooledConnection;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.SQLException;

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
        Connection connection = connectionInformation.getConnection(); //数据库，用户名、密码等
        MysqlConnection mysqlConnection = null;

//        String schema = connection.getSchema();
        Driver driver = connectionInformation.getDriver(); //非null 但是暂时未发现有效内容
        String sqlWithValues1 = connectionInformation.getSqlWithValues(); // ""
        PooledConnection pooledConnection = connectionInformation.getPooledConnection(); //null


        ConnectionInformation connectionInformation1 = connectionInformation.getConnectionInformation();
        boolean c1 = connectionInformation == connectionInformation1; //true


        System.out.println("lalalall");
//        log.info("loggable : {}, timeElapsedNanos : {}, category : {}", JSON.toJSONString(loggable), timeElapsedNanos, JSON.toJSONString(category), e);
    }

}
