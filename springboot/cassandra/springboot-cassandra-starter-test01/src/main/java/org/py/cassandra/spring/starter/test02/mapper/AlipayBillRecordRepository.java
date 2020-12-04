package org.py.cassandra.spring.starter.test02.mapper;

import org.py.cassandra.spring.starter.test02.entity.AlipayBillRecord;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AlipayBillRecordRepository extends CrudRepository<AlipayBillRecord, String> {

    @Query("select * from alipay_bill_record where app_id=?0 and bill_day=?1")
    List<AlipayBillRecord> queryByAppIdAndDay(String appId, String billDay);

    /**
     * 根据条件查询总条数
     * @param appId
     * @return
     */
    @Query("select count(*) from alipay_bill_record where app_id=?0 and bill_day=?1")
    long queryPageCount(String appId,String billDay);

    /**
     * 分页查询数据
     * @param appId
     * @param pageRequest
     * @return
     */
    @Query("select * from alipay_bill_record where app_id=?0 and bill_day=?1")
    Slice<AlipayBillRecord> queryByPage(String appId, String billDay, Pageable pageRequest);
}
