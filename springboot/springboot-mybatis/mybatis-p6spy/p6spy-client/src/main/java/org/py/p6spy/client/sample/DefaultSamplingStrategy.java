package org.py.p6spy.client.sample;

import org.py.p6spy.client.entry.SQLDetail;
import org.py.p6spy.client.plugs.SQLAnalyseConfig;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class DefaultSamplingStrategy {

    private final Map<String, Integer> sqlCountRecordMap = new HashMap<>();

    public synchronized boolean checkSimple(SQLDetail sqlDetail) {
        String key = String.format("%s_%s_%s_%s", sqlDetail.getAppId(), sqlDetail.getDbName(), sqlDetail.getCategory(), sqlDetail.getOperator());
        Integer count = sqlCountRecordMap.get(key);
        if(count == null) {
            count = 0;
        }
        int samplingFrequency = SQLAnalyseConfig.getSamplingFrequency();
        sqlCountRecordMap.put(key, (count + 1) % samplingFrequency);
        return count % samplingFrequency == 0;
    }

}
