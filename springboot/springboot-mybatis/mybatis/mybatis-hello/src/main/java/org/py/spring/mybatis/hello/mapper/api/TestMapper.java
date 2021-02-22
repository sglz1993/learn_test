package org.py.spring.mybatis.hello.mapper.api;


import org.py.spring.mybatis.hello.mapper.entity.Test;

//@Mapper
public interface TestMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);
}