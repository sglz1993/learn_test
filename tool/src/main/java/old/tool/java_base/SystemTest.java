package old.tool.java_base;

import org.junit.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.UUID;

/**
 * @author: pengyue.du
 * @time: 2020/7/28 1:16 下午
 */
public class SystemTest {

    @Test
    public void test() throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost().getHostName());
    }

    @Test
    public void test2() {
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println(UUID.randomUUID().toString().replace("-",""));
    }

}
