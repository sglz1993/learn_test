package org.py.db.starter.mapper.entity;

/**
 * @table t
 * @author pengyue.du
 * @date 2020-08-04 11:08:03
 */
//@TableName("t")
public class T {
    /**
     */
    private Integer id;

    /**
     */
    private String name;

    /**
     */
    private boolean tBool;

    public T(String name) {
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

    public boolean gettBool() {
        return tBool;
    }

    public void settBool(boolean tBool) {
        this.tBool = tBool;
    }
}