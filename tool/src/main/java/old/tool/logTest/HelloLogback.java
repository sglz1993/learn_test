package old.tool.logTest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.jvm.hotspot.HelloWorld;

/**
 *
 * @see <a href='http://www.slf4j.org/manual.html#swapping'>官网参考</a>
 * 需要一个slf4j api 需要一个 绑定器
 * (@see <a href='https://www.cnblogs.com/quchunhui/p/5783172.html'>博客参考</a>)
 *
 *     <dependency>
 *       <groupId>org.slf4j</groupId>
 *       <artifactId>slf4j-api</artifactId>
 *       <version>1.7.7</version>
 *     </dependency>
 *     <dependency>
 *       <groupId>ch.qos.logback</groupId>
 *       <artifactId>logback-core</artifactId>
 *       <version>1.1.7</version>
 *     </dependency>
 *     <dependency>
 *       <groupId>ch.qos.logback</groupId>
 *       <artifactId>logback-access</artifactId>
 *       <version>1.1.7</version>
 *     </dependency>
 *
 *     对接
 *     <dependency>
 *       <groupId>ch.qos.logback</groupId>
 *       <artifactId>logback-classic</artifactId>
 *       <version>1.1.7</version>
 *     </dependency>
 *
 * @author: pengyue.du
 * @time: 2020/6/16 10:24 上午
 */
public class HelloLogback {


    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger(HelloWorld.class);
        logger.info("Hello World");
    }
}
