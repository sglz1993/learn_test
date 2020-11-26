package org.py.p6spy.user.v1.mapper.api;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.py.p6spy.user.v1.mapper.entity.TestEntry;

import java.util.List;


@Mapper
public interface TestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(TestEntry record);

    int insertSelective(TestEntry record);

    TestEntry selectByPrimaryKey(Integer id);

    TestEntry selectTest(Integer id);

    int updateByPrimaryKeySelective(TestEntry record);

    int updateByPrimaryKey(TestEntry record);

    List<TestEntry> call(Integer num);

    void bash(@Param("testList") List<TestEntry> testList);
}