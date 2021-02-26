package test;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.py.spring.mybatisplus.hello.MyBatisPlusHelloApplication;
import org.py.spring.mybatisplus.hello.mapper.api.TestMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MyBatisPlusHelloApplication.class)
public class MybatisTest {

    @Resource
    private TestMapper testMapper;

    /**
     * SELECT COUNT(*) FROM test_db.t WHERE t_bool = false
     * SELECT id,name,t_bool FROM test_db.t WHERE t_bool=false LIMIT ?
     *
     * 标记删除的自动增加
     */
    @Test
    public void test() {
        Page<org.py.spring.mybatisplus.hello.mapper.entity.Test> testPage = testMapper.selectPage(new Page<org.py.spring.mybatisplus.hello.mapper.entity.Test>(1, 10), null);
        log.info(JSON.toJSONString(testPage.getRecords(), true));
    }


}
