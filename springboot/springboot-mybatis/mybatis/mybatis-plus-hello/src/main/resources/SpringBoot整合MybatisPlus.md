官网：https://baomidou.com/  
说明！！！：对Mybatis完全兼容
### 1. 依赖
```
        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.21</version>
        </dependency>
        <!-- https://mvnrepository.com/artifact/com.baomidou/mybatis-plus-boot-starter -->
        <dependency>
            <groupId>com.baomidou</groupId>
            <artifactId>mybatis-plus-boot-starter</artifactId>
            <version>3.4.2</version>
        </dependency>
```
### 2. 参数配置：完全是Mybatis的配置
```
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
```
### 3. 其他配置：
```
    需要指定Mapper接口位置，有三种方式：
            1. 在接口类上添加注解：org.apache.ibatis.annotations.Mapper
            2. 通过注解：org.mybatis.spring.annotation.MapperScan 指定扫描的接口包
                需要说明的是：
                    1. 该扫描包下的类会全部注册为spring容器，注意Bean重复
                    2. 该注解为可重复注解，可以注释多个，也可以通过该注解容器类注释
```
### 4. 其他使用说明：
```
    4.1 Mapper类需要继承BaseMapper，减少基本的几个SQL
        public interface UserMapper extends BaseMapper<User> {
        
        }
    4.2 默认将类名、属性名转下划线方式，假如不一致，需要baomidou注解配合:  mybatisplus.annotation 包下
    4.3 MybatisPlus的条件构造器主要为两个：QueryWrapper 和 UpdateWrapper，通过Wrappers创建
    4.4 几个特殊的函数：
        func 方法(主要方便在出现if...else下调用不同方法能不断链)
            例: func(i -> if(true) {i.eq("id", 1)} else {i.ne("id", 1)})
        nested 暂时不知道什么时候会用：正常嵌套 不带 AND 或者 OR
        apply and拼接 sql, 该方法可用于数据库函数 动态入参的params对应前面applySql内部的{index}部分.这样是不会有sql注入风险的,反之会有!
        last 无视优化规则直接拼接到 sql 的最后, 只能调用一次,多次调用以最后一次为准 有sql注入的风险,请谨慎使用
    4.5 String sqlSelect = wrapper.getCustomSqlSegment(); 获取的SQL当有条件时是带Where的，无条件时不带Where
    4.6 主键需要注意，默认主键为id，否则需要注解明确标注
    4.7 mybatis-plus.global-config.db-config.id-type=ASSIGN_ID ，默认id为雪花片算法，需要注意是否取消改配置
```
### 11.1 说明
mybatis-plus-boot-starter：包含
```
    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus</artifactId>
      <version>3.4.2</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>org.springframework.boot</groupId>
      <artifactId>spring-boot-starter-jdbc</artifactId>
      <scope>compile</scope>
    </dependency>
```
mybatis-plus：包含
```
    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-extension</artifactId>
      <version>3.4.2</version>
      <scope>compile</scope>
    </dependency>
```
mybatis-plus-extension：包含
```
    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-core</artifactId>
      <version>3.4.2</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>2.0.5</version>
      <scope>compile</scope>
    </dependency>
```
mybatis-plus-core：包含
```
    <dependency>
      <groupId>com.baomidou</groupId>
      <artifactId>mybatis-plus-annotation</artifactId>
      <version>3.4.2</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>com.github.jsqlparser</groupId>
      <artifactId>jsqlparser</artifactId>
      <version>4.0</version>
      <scope>compile</scope>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.6</version>
      <scope>compile</scope>
    </dependency>
```



