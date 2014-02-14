package kafka.util;

import java.util.Properties;

import kafka.Consts;
import kafka.consumer.ConsumerConfig;
import kafka.producer.ProducerConfig;

public class SimpleConfig {

    public static final ProducerConfig getProducerConfig(String zkConnectAddress,
            String brokerListAddress, String producerType) {
        Properties p = new Properties();
        p.put(Consts.ZK_CONNECT_KEY, zkConnectAddress);
        p.put(Consts.KEY_SERIALIZER_CLASS_KEY, Consts.KEY_SERIALIZER_CLASS_VALUE);
        p.put(Consts.VALUE_SERIALIZER_CLASS_KEY, Consts.VALUE_SERIALIZER_CLASS_VALUE);
        p.put(Consts.METADATA_BROKER_LIST_KEY, brokerListAddress);
        p.put(Consts.PARTITIONER_CLASS_KEY, Consts.PARTITIONER_CLASS_VALUE);
        if ("async".equals(producerType)) {
            p.put(Consts.QUEUE_MAX_MESSAGES_KEY, Consts.QUEUE_MAX_MESSAGES_VALUE);
            p.put(Consts.PRODUCER_TYPE_KEY, producerType);
            p.put(Consts.BATCH_NUM_MESSAGES_KEY, Consts.BATCH_NUM_MESSAGES_VALUE);
            p.put(Consts.COMPRESSION_CODEC_KEY, Consts.COMPRESSION_CODEC_VALUE);
            p.put(Consts.QUEUE_MAX_TIEM_KEY, Consts.QUEUE_MAX_TIEM_VALUE);
        }
        return new ProducerConfig(p);
    }

    public static final ProducerConfig getProducerConfigTwo(String zkConnectAddress,
            String brokerListAddress, String producerType, String compressinType) {
        Properties p = new Properties();
        p.put(Consts.ZK_CONNECT_KEY, zkConnectAddress);
        p.put(Consts.KEY_SERIALIZER_CLASS_KEY, Consts.KEY_SERIALIZER_CLASS_VALUE);
        p.put(Consts.VALUE_SERIALIZER_CLASS_KEY, Consts.VALUE_SERIALIZER_CLASS_VALUE);
        p.put(Consts.METADATA_BROKER_LIST_KEY, brokerListAddress);
        p.put(Consts.PARTITIONER_CLASS_KEY, Consts.PARTITIONER_CLASS_VALUE);
        if ("async".equals(producerType)) {
            p.put(Consts.QUEUE_MAX_MESSAGES_KEY, Consts.QUEUE_MAX_MESSAGES_VALUE);
            p.put(Consts.PRODUCER_TYPE_KEY, producerType);
            p.put(Consts.BATCH_NUM_MESSAGES_KEY, Consts.BATCH_NUM_MESSAGES_VALUE);
            p.put(Consts.COMPRESSION_CODEC_KEY, compressinType);
            p.put(Consts.QUEUE_MAX_TIEM_KEY, Consts.QUEUE_MAX_TIEM_VALUE);
        }
        return new ProducerConfig(p);
    }

    public static final ConsumerConfig getConsumerConfig(String zkConnectAddress) {
        Properties p = new Properties();
        p.put(Consts.ZK_CONNECT_KEY, zkConnectAddress);
        p.put(Consts.GROUP_ID_KEY, Consts.GROUP_ID_VALUE);
        p.put("zookeeper.session.timeout.ms", "400000");
        p.put("rebalance.max.retries", "100000");
        // p.put("fetch.message.max.bytes","1000000"); 54
        // p.put("queued.max.message.chunks","10"); 55
        // p.put("fetch.wait.max.mss","5000"); 56

        // p.put(ApplicationConfig.ZK_SYNC_TIME_KEY, 58
        // ApplicationConfig.ZK_SYNC_TIME_VALUE); 59
        // p.put(ApplicationConfig.AUTO_COMMIT_INTERVAL_KEY, 60
        // ApplicationConfig.AUTO_COMMIT_INTERVAL_VALUE); 61
        // p.put(ApplicationConfig.AUTO_COMMIT_ENABLE_KEY, 62
        // ApplicationConfig.AUTO_COMMIT_ENABLE_VALUE); 63
        // p.put(ApplicationConfig.REBALANCE_MAX_RETRIES_KEY, 64
        // ApplicationConfig.REBALANCE_MAX_RETRIES_VALUE); 65
        return new ConsumerConfig(p);
    }
}
