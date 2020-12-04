package org.py.spring.cassandra.core.test01.entity;

import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//    id int,
//            address text,
//            name text,
//            age int,
//            height int,
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "teacher")
public class Teacher {

    @PartitionKey
    private Integer id;

    @Column
    private String address;

    @Column
    private String name;

    @Column
    private Integer age;

    @Column
    private Integer height;


}
