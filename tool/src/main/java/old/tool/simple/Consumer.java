package old.tool.simple;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * @Author pengyue.du
 * @Date 2020/5/14 11:22 上午
 * @Description
 */
public class Consumer {

    public static void main(String[] args) {

        Properties properties = new Properties();
        //连接kafka集群
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        //指定消费者组
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "practice-consumer");
        //设置 offset自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        //自动提交间隔时间
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        //key反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
        //value反序列化
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        //创建消费者
        KafkaConsumer<Integer,String> consumer = new KafkaConsumer<Integer,String>(properties);

        consumer.subscribe(Collections.singleton("data_report-100420000-hu_data_report"));
//        consumer.subscribe(Lists.newArrayList("test"));
        while(true){
            ConsumerRecords<Integer, String> records = consumer.poll(Duration.ofSeconds(100));
            for (ConsumerRecord<Integer, String> record : records) {
                System.out.println(record.key() + " " + record.value() + " -> offset:" + record.offset());
            }
        }
    }


}
