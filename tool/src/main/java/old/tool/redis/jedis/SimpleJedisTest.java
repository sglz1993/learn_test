package old.tool.redis.jedis;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @Author pengyue.du
 * @Date 2020/9/7 6:59 下午
 * @Description
 */
public class SimpleJedisTest {

    @Test
    public void test() {
        Jedis jedis = new Jedis("localhost");
        String set = jedis.set("lalalal2", "1", "NX", "EX", 100);
        System.out.println(set);
    }

}
