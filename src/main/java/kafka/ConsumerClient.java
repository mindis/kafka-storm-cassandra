package kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kafka.Metric.StorageRequest;
import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import kafka.util.MetricKeyDecoder;
import kafka.util.MetricValueDecoder;
import kafka.util.SimpleConfig;

public class ConsumerClient {
    private final String topic;
    private final ConsumerConnector connector;
    private final static ConsumerConfig config = SimpleConfig
            .getConsumerConfig("192.168.198.129:2181");
    private final static MetricKeyDecoder keyDecoder = new MetricKeyDecoder();
    private final static MetricValueDecoder valueDecoder = new MetricValueDecoder();

    public ConsumerClient(String topic) {
        this.topic = topic;
        connector = Consumer.createJavaConsumerConnector(config);
    }

    public ConsumerIterator<String, StorageRequest> getIterator() {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(1));
        Map<String, List<KafkaStream<String, StorageRequest>>> consumerMap = connector
                .createMessageStreams(topicCountMap, keyDecoder, valueDecoder);
        KafkaStream<String, StorageRequest> stream = consumerMap.get(topic).get(0);
        return stream.iterator();
    }
}