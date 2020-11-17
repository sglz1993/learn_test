package org.py.test.p6spy.test05.p6spy;

import com.p6spy.engine.common.*;
import com.p6spy.engine.event.SimpleJdbcEventListener;
import com.p6spy.engine.logging.Category;

import java.sql.SQLException;

/**
 * @Author pengyue.du
 * @Date 2020/9/10 4:29 下午
 * @Description
 */
public class MyJdbcEventListener extends SimpleJdbcEventListener {

    public static final MyJdbcEventListener INSTANCE = new MyJdbcEventListener();


    @Override
    public void onAfterAnyExecute(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
        logElapsed(statementInformation, timeElapsedNanos, Category.STATEMENT, e);
    }

    @Override
    public void onAfterExecuteBatch(StatementInformation statementInformation, long timeElapsedNanos, int[] updateCounts, SQLException e) {
        logElapsed(statementInformation, timeElapsedNanos, Category.BATCH, e);
    }

    @Override
    public void onAfterCommit(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
        logElapsed(connectionInformation, timeElapsedNanos, Category.COMMIT, e);
    }

    @Override
    public void onAfterRollback(ConnectionInformation connectionInformation, long timeElapsedNanos, SQLException e) {
        logElapsed(connectionInformation, timeElapsedNanos, Category.ROLLBACK, e);
    }

    @Override
    public void onAfterAnyAddBatch(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
        logElapsed(statementInformation, timeElapsedNanos, Category.BATCH, e);
    }

    @Override
    public void onAfterGetResultSet(StatementInformation statementInformation, long timeElapsedNanos, SQLException e) {
        logElapsed(statementInformation, timeElapsedNanos, Category.RESULTSET, e);
    }

    @Override
    public void onAfterResultSetGet(ResultSetInformation resultSetInformation, int columnIndex, Object value, SQLException e) {
        resultSetInformation.setColumnValue(Integer.toString(columnIndex), value);
    }

    @Override
    public void onAfterResultSetGet(ResultSetInformation resultSetInformation, String columnLabel, Object value, SQLException e) {
        resultSetInformation.setColumnValue(columnLabel, value);
    }

    @Override
    public void onBeforeResultSetNext(ResultSetInformation resultSetInformation) {
        if (resultSetInformation.getCurrRow() > -1) {
            // Log the columns that were accessed except on the first call to ResultSet.next().  The first time it is
            // called it is advancing to the first row.
            resultSetInformation.generateLogMessage();
        }
    }

    @Override
    public void onAfterResultSetNext(ResultSetInformation resultSetInformation, long timeElapsedNanos, boolean hasNext, SQLException e) {
        if (hasNext) {
            logElapsed(resultSetInformation, timeElapsedNanos, Category.RESULT, e);
        }
    }

    @Override
    public void onAfterResultSetClose(ResultSetInformation resultSetInformation, SQLException e) {
        if (resultSetInformation.getCurrRow() > -1) {
            // If the result set has not been advanced to the first row, there is nothing to log.
            resultSetInformation.generateLogMessage();
        }
    }

    protected void logElapsed(Loggable loggable, long timeElapsedNanos, Category category, SQLException e) {
        MyRecord.logElapsed(loggable, timeElapsedNanos, category, e);
    }


}
