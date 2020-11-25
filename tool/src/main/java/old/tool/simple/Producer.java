package old.tool.simple;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.IntegerSerializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @Author pengyue.du
 * @Date 2020/5/14 11:21 上午
 * @Description
 */
public class Producer {

    public static void main(String[] args) {
        Properties properties = new Properties();
        //连接kafka集群
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        //key序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, IntegerSerializer.class.getName());
        //value序列化
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //创建生产者
        KafkaProducer<Integer,String> producer = new KafkaProducer<Integer,String>(properties);

        int num = 0;
        while(num < 10){
            String msg = "cyy kafka test" + num;
            try {
                producer.send(new ProducerRecord<Integer, String>("test",num,msg)).get();
                TimeUnit.SECONDS.sleep(2);
                num++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }


}
