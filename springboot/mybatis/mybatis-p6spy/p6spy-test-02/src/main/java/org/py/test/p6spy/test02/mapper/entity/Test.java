package org.py.test.p6spy.test02.mapper.entity;

/**
 * @table t
 * @author pengyue.du
 * @date 2020-08-04 11:08:03
 */
public class Test {
    /**
     */
    private Integer id;

    /**
     */
    private String name;

    public Test(String name) {
        this.name = name;
    }

    public Test() {
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
}