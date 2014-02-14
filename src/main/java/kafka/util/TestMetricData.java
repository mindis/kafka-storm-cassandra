package kafka.util;

import java.util.Random;

import kafka.Metric.StorageRequest;
import kafka.Metric.StorageRequest.Builder;

public class TestMetricData {

    public static final kafka.Metric.StorageRequest createTestData(String metric) {
        Builder m = StorageRequest.newBuilder();
        m.setName(metric);
        m.setTimestamp(System.currentTimeMillis()/1000);
        m.setValue(new Random().nextInt(100000));
        return m.build();
    }
}
