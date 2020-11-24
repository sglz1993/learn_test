package org.py.test.mybatis.test02.mapper.api;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.py.test.mybatis.test02.mapper.entity.Test;

import java.util.List;

@Mapper
public interface TestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    Test selectTest(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);

    List<Test> call(Integer num);

    int bash(@Param("testList") List<Test> testList);
}