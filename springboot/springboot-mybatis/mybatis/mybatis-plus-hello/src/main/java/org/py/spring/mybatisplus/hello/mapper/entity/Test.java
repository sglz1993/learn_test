package org.py.spring.mybatisplus.hello.mapper.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

/**
 * @table t
 * @author pengyue.du
 * @date 2020-08-04 11:08:03
 */
@TableName(value = "t", schema = "test_db")
public class Test extends Model<Test> {
    /**
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     */
    private String name;

    /**
     */
    private boolean tBool;

    public Test(String name) {
        this.name = name;
        tBool = name.hashCode() % 2 == 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

//    public boolean gettBool() {
//        return tBool;
//    }
//
//    public void settBool(boolean tBool) {
//        this.tBool = tBool;
//    }

    public boolean getToolBool() {
        return tBool;
    }

    public boolean gettBool() {
        return tBool;
    }

    public void setToolBool(boolean tBool) {
        this.tBool = tBool;
    }


}