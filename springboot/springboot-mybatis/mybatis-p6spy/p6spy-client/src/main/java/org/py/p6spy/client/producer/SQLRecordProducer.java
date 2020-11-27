package org.py.p6spy.client.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.py.p6spy.client.config.Constant;
import org.py.p6spy.client.entry.SQLDetail;
import org.py.p6spy.client.plugs.SQLAnalyseConfig;
import org.py.p6spy.client.util.Util;
import java.nio.charset.StandardCharsets;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * async kafka producer
 * @author pengyue.du
 */
@Slf4j
public class SQLRecordProducer {

    private volatile static ExecutorService executorService;

    private volatile static KafkaProducer<byte[], byte[]> kafkaProducer;

    private final static String topic = "infrastructure_sql_record_p6spy_topic";

    public static void sendSQLRecord(SQLDetail sqlDetail) {
        getSQLRecordSendThreadPoll().submit(() -> {
            getProducer().send(new ProducerRecord<>(topic, Util.getGson().toJson(sqlDetail).getBytes(StandardCharsets.UTF_8)));
        });
    }

    private static ExecutorService getSQLRecordSendThreadPoll() {
        if(executorService == null) {
            synchronized (SQLRecordProducer.class) {
                if(executorService == null) {
                    executorService = new ThreadPoolExecutor(2, 2, 5L, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(Constant.DEFAULT_QUEUE_SIZE), Executors.defaultThreadFactory(), (r, executor) -> {
                            log.warn("SQLRecordSendThreadPoll already full");
                    });
                }
            }
        }
        return executorService;
    }

    private static KafkaProducer<byte[], byte[]> getProducer() {
        if(kafkaProducer == null) {
            synchronized (SQLRecordProducer.class) {
                if(kafkaProducer == null) {
                    kafkaProducer = new KafkaProducer<>(getProducerConfig());
                    Runtime.getRuntime().addShutdownHook(new Thread("SQLRecordProducer-Close") {
                        @Override
                        public void run() {
                            kafkaProducer.close();
                        }
                    });
                }
            }
        }
        return kafkaProducer;
    }

    private static Properties getProducerConfig() {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", SQLAnalyseConfig.getBootstrapServers());
        properties.put("request.required.acks", "1");
        properties.put("retries", 3);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("client.id", getClientId());
        properties.put("key.serializer", ByteArraySerializer.class.getName());
        properties.put("value.serializer", ByteArraySerializer.class.getName());
        return properties;
    }

    private static String getClientId() {
        return String.format("SQLRecordProducer_%s_%s", SQLAnalyseConfig.getAppId(), SQLAnalyseConfig.getServiceName());
    }


}
