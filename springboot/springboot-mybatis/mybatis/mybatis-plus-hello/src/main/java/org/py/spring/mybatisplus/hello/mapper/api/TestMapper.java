package org.py.spring.mybatisplus.hello.mapper.api;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.py.spring.mybatisplus.hello.mapper.entity.Test;

//@Mapper
public interface TestMapper extends BaseMapper<Test> {
    int deleteByPrimaryKey(Integer id);

//    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);
}