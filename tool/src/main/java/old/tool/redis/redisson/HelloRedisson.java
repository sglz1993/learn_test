package old.tool.redis.redisson;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

/**
 * @author: pengyue.du
 * @time: 2020/6/10 3:28 下午
 */
public class HelloRedisson {

    public static void main(String[] args) {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://127.0.0.1:6379");

        RedissonClient client = Redisson.create(config);
        RLock mylock = client.getLock("mylock");
        mylock.lock();
        mylock.unlock();

    }



}
