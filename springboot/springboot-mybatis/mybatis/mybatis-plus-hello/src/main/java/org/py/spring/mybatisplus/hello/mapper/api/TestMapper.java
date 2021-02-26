package org.py.spring.mybatisplus.hello.mapper.api;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.py.spring.mybatisplus.hello.mapper.entity.Test;

import java.util.List;

//@Mapper
public interface TestMapper extends BaseMapper<Test> {
    int deleteByPrimaryKey(Integer id);

//    int insert(Test record);

    int insertSelective(Test record);

    Test selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Test record);

    int updateByPrimaryKey(Test record);

    @Select("select * from t ${ew.customSqlSegment}")
    List<Test> selectAllTest(@Param(Constants.WRAPPER) Wrappers wrappers);
}