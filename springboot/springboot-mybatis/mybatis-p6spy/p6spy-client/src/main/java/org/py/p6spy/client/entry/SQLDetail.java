package org.py.p6spy.client.entry;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 哪个服务，哪个库，读/写，原始SQL，执行的SQL，执行时间，哪个用户操作的，哪个pod调用，记录时间
 * @author pengyue.du
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SQLDetail {

    /**
     * 来源服务名称
     */
    private String serviceName;

    /**
     * 操作的数据库
     */
    private String dbName;

    /**
     * 操作类型
     * read ： 读
     * write ： 写
     * other ： 其他
     */
    private String operationType;

    /**
     * 原始SQL
     */
    private String originSQL;

    /**
     * 参数
     */
    private Map<Integer, String> param;

    /**
     * SQL执行时间，单位毫秒
     */
    private long execTime;

    /**
     * 执行SQL的用户名
     */
    private String operator;

    /**
     * 从哪个pod发起的执行请求
     */
    private String fromPod;

    /**
     * 记录时间，单位毫秒的时间戳
     */
    private long recordTime;

    /**
     * 保留值，上层操作类型
     */
    private String category;

}
