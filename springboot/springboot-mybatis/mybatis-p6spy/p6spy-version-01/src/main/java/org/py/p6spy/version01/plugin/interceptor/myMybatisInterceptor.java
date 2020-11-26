package org.py.p6spy.version01.plugin.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.transaction.Transaction;

import java.sql.Statement;
import java.util.List;

/**
 * @Author pengyue.du
 * @Date 2020/9/9 3:42 下午
 * @Description
 */
@Slf4j
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = Executor.class, method = "commit", args = {boolean.class}),
        @Signature(type = Executor.class, method = "rollback", args = {boolean.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = { Statement.class })})
public class myMybatisInterceptor implements Interceptor {


    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object target = invocation.getTarget();

        long startTime = System.currentTimeMillis();
        if(target instanceof StatementHandler) {
            StatementHandler statementHandler = (StatementHandler)target;

            try {
                return invocation.proceed();
            } finally {
                long endTime = System.currentTimeMillis();
                long sqlCost = endTime - startTime;

                BoundSql boundSql = statementHandler.getBoundSql();
                String sql = boundSql.getSql();
                List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
                sql = beautifySql(sql);
                log.warn("SQL：[" + sql + "]执行耗时[" + sqlCost + "ms]");
                log.warn("parameter：[" + parameterMappingList + "]");
            }

        }else if(target instanceof Executor) {
            Executor executor = (Executor)target;
            try {
                return invocation.proceed();
            } finally {
                long endTime = System.currentTimeMillis();
                long sqlCost = endTime - startTime;
                Transaction transaction = executor.getTransaction();
//                BoundSql boundSql = executor.commit();
//                String sql = boundSql.getSql();
//                List<ParameterMapping> parameterMappingList = boundSql.getParameterMappings();
//                sql = beautifySql(sql);
//                log.warn("SQL：[" + sql + "]执行耗时[" + sqlCost + "ms]");
//                log.warn("parameter：[" + parameterMappingList + "]");
            }
        }
        return null;
    }

    /**
     * 美化Sql
     */
    private String beautifySql(String sql) {
        // sql = sql.replace("\n", "").replace("\t", "").replace("  ", " ").replace("( ", "(").replace(" )", ")").replace(" ,", ",");
        sql = sql.replaceAll("[\\s\n ]+"," ");
        return sql;
    }

}
