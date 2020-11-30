package org.py.p6spy.client.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.Validate;
import org.apache.kafka.clients.admin.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.ByteArraySerializer;
import org.py.p6spy.client.config.Constant;
import org.py.p6spy.client.entry.SQLDetail;
import org.py.p6spy.client.plugs.SQLAnalyseConfig;
import org.py.p6spy.client.util.Util;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
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

    private final static String topic = Constant.DEFAULT_TOPIC;

    public static void sendSQLRecord(SQLDetail sqlDetail) {
        getSQLRecordSendThreadPoll().submit(() -> {
            log.debug("send sql record:{}", Util.getGson().toJson(sqlDetail));
            getProducer().send(new ProducerRecord<>(topic, Util.getGson().toJson(sqlDetail).getBytes(StandardCharsets.UTF_8)));
        });
    }

    private static ExecutorService getSQLRecordSendThreadPoll() {
        if(executorService == null) {
            synchronized (SQLRecordProducer.class) {
                if(executorService == null) {
                    executorService = new ThreadPoolExecutor(2, 2, 5L, TimeUnit.SECONDS,
                            new LinkedBlockingQueue<>(Constant.DEFAULT_QUEUE_SIZE), Executors.defaultThreadFactory(), (r, executor) -> log.warn("SQLRecordSendThreadPoll already full"));
                }
            }
        }
        return executorService;
    }

    private static KafkaProducer<byte[], byte[]> getProducer() {
        if(kafkaProducer == null) {
            synchronized (SQLRecordProducer.class) {
                if(kafkaProducer == null) {
                    createTopicIfNotPresent(SQLAnalyseConfig.getBootstrapServers(), topic, SQLAnalyseConfig.getPartitions(), SQLAnalyseConfig.getReplics());
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
        properties.put("acks", "1");
        properties.put("retries", 3);
        properties.put("batch.size", 16384);
        properties.put("linger.ms", 1);
        properties.put("buffer.memory", 33554432);
        properties.put("client.id", getClientId());
        properties.put("key.serializer", ByteArraySerializer.class.getName());
        properties.put("value.serializer", ByteArraySerializer.class.getName());
        return properties;
    }


    private static void createTopicIfNotPresent(String bootstrapServers, String topic, int partitions, int replics) {
        Validate.notEmpty(bootstrapServers, "Kafka bootstrap server can not be null.");
        Validate.notEmpty(topic, "Kafka topic can not be null.");
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        AdminClient adminClient = AdminClient.create(props);
        if (!checkTopicExist(topic, adminClient)) {
            createTopic(adminClient, topic, partitions, replics);
        }
    }

    private static void createTopic(AdminClient adminClient, String topicName, int partitions, int replicationFactor) {
        NewTopic topic = new NewTopic(topicName, partitions, (short) replicationFactor);
        topic.configs(Util.asMap("retention.bytes", String.valueOf(10000 * 2000), "retention.ms", String.valueOf(2 * 24 * 60 * 60 * 1000)));
        Collection<NewTopic> topics = Collections.singletonList(topic);
        CreateTopicsResult result = adminClient.createTopics(topics);
        try {
            result.all().get();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage(), e);
        }
    }

    public static boolean checkTopicExist(String topicName, AdminClient adminClient) {
        Collection<String> topics = Collections.singletonList(topicName);
        DescribeTopicsResult result = adminClient.describeTopics(topics);
        try {
            Map<String, TopicDescription> topicDescriptionMap = result.all().get(1000, TimeUnit.MILLISECONDS);
            if (topicDescriptionMap.containsKey(topicName)) {
                return true;
            }
        } catch (Exception e) {
            log.warn("check topic error", e);
        }
        return false;
    }

    private static String getClientId() {
        return String.format("SQLRecordProducer_%s_%s", SQLAnalyseConfig.getAppId(), SQLAnalyseConfig.getServiceName());
    }


}
