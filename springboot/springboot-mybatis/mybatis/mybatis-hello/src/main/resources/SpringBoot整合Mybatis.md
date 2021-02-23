1. 依赖：
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-starter</artifactId>
      <version>2.1.3</version>
    </dependency>
    //包含了：Spring事务依赖、HikariCP连接池、Mybatis依赖包、Mybatis对Spring的定制包

    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>8.0.21</version>
    </dependency>
    // 数据库驱动需要单独提供，没有默认的
    
    <dependency>
      <groupId>org.mybatis.spring.boot</groupId>
      <artifactId>mybatis-spring-boot-autoconfigure</artifactId>
    </dependency>
    // spring自动注入的一些类
        
2. 参数配置：
    // 数据库连接用户
    spring.datasource.username=root
    // 数据库连接用户密码
    spring.datasource.password=123456
    // 数据库连接url
    spring.datasource.url=jdbc:mysql://localhost:3306/test_db?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC
    // 驱动类
    spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
    // 指名Mapper文件所在的路径
    mybatis.mapper-locations=classpath:mapper/*Mapper.xml
    // 在Mapper文件中使用别名的话，指定别名的包， (Package delimiters are ",; \t\n")  可以使用一些符号写多个包
    mybatis.type-aliases-package=org.py.spring.mybatis.hello.mapper.entity
    // 就是设置某个包的日志等级
    logging.level.org.py=debug
3. 其他配置：
    需要指定Mapper接口位置，有三种方式：
        1. 在接口类上添加注解：org.apache.ibatis.annotations.Mapper
        2. 通过注解：org.mybatis.spring.annotation.MapperScan 指定扫描的接口包
            需要说明的是：
                1. 该扫描包下的类会全部注册为spring容器，注意Bean重复
                2. 该注解为可重复注解，可以注释多个，也可以通过该注解容器类注释

1.1. 依赖说明：
mybatis-spring-boot-starter：
包含：
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
    </dependency>


spring-boot-starter-jdbc：包含
    <dependency>
      <groupId>com.zaxxer</groupId>
      <artifactId>HikariCP</artifactId>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <scope>compile</scope>
    </dependency>

spring-jdbc：包含
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-tx</artifactId>
      <version>5.2.6.RELEASE</version>
      <scope>compile</scope>
    </dependency>

