package org.py.test.p6spy.test05.p6spy;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.py.common.thread.StackUtil;

import java.time.LocalDateTime;

/**
 * @Author pengyue.du
 * @Date 2020/8/4 2:59 下午
 * @Description
 */
@Slf4j
public class P6SpyLogger implements MessageFormattingStrategy {

    /**
     * # Custom log message format used ONLY IF logMessageFormat is set to com.p6spy.engine.spy.appender.CustomLineFormat
     * # default is %(currentTime)|%(executionTime)|%(category)|connection%(connectionId)|%(sqlSingleLine)
     * # Available placeholders are:
     * #   %(connectionId)            the id of the connection
     * #   %(currentTime)             the current time expressing in milliseconds
     * #   %(executionTime)           the time in milliseconds that the operation took to complete
     * #   %(category)                the category of the operation
     * #   %(effectiveSql)            the SQL statement as submitted to the driver
     * #   %(effectiveSqlSingleLine)  the SQL statement as submitted to the driver, with all new lines removed
     * #   %(sql)                     the SQL statement with all bind variables replaced with actual values
     * #   %(sqlSingleLine)           the SQL statement with all bind variables replaced with actual values, with all new lines removed
     * #customLogMessageFormat=%(currentTime)|%(executionTime)|%(category)|connection%(connectionId)|%(sqlSingleLine)
     * @param connectionId
     * @param now
     * @param elapsed
     * @param category
     * @param prepared
     * @param sql
     * @param url
     * @return
     */
    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql, String url) {
        StackUtil.printThreadStack(10);
        log.info("connectionId:{}, now:{}, elapsed:{}, category:{}, prepared:{}, sql:{}, url:{}", connectionId, now, elapsed, category, prepared, sql, url);
        return !"".equals(sql.trim()) ? "[ " + LocalDateTime.now() + " ] --- | took "
                + elapsed + "ms | " + category + " | connection " + connectionId + "\n "
                + sql + ";" : "";
    }
}
