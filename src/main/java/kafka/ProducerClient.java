package kafka;

import java.util.Timer;
import java.util.TimerTask;

import kafka.Metric.StorageRequest;
import kafka.common.FailedToSendMessageException;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import kafka.util.SimpleConfig;
import kafka.util.TestMetricData;

public class ProducerClient extends Thread {

    private final String topic;

    private final int totalNum;

    private static ProducerConfig config = null;

    public ProducerClient(int totalNum, String topic, String zkConnectAddress,
            String brokerListAddress, String producerType) {
        this.totalNum = totalNum;
        this.topic = topic;
        config = SimpleConfig.getProducerConfig(zkConnectAddress, brokerListAddress, producerType);
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                long start = System.currentTimeMillis();
                int count = 0;
                long takeTime = 0;
                do {
                    sendMsg();
                    count++;
                } while ((takeTime = System.currentTimeMillis() - start) == 0);
                System.out.println(Thread.currentThread().getName() + "每秒发送：" + count * totalNum
                        * 1000 / takeTime);
            }
        }, 1000, 5000);
    }

    private void sendMsg() {
        Producer<String, StorageRequest> producer = new Producer<String, StorageRequest>(config);
        try {
            for (int i = 0; i < totalNum; i++) {
                StorageRequest metric = TestMetricData.createTestData("wangyi" + i);
                producer.send(new KeyedMessage<String, StorageRequest>(topic, "key", metric));
            }
        } catch (FailedToSendMessageException ex) {
            ex.printStackTrace();
        } finally {
            producer.close();
        }
    }

    public static void main(String[] args) {
        final String topic = "C11_test";
        final String zkConnectAddress = "192.168.198.129:2181";
        final String brokerListAddress = "192.168.198.129:9092";
        int totalNum = 5;
        int threadNum =1;
        String producerType = "async";
        for (int i = 0; i < threadNum; i++) {
            new ProducerClient(totalNum, topic, zkConnectAddress, brokerListAddress, producerType)
                    .start();
        }
    }
}