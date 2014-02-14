package kafka.util;

import kafka.producer.Partitioner;
import kafka.utils.VerifiableProperties;

public class MetricPartitioner implements Partitioner<String> {

    public MetricPartitioner(VerifiableProperties props) {
    }

    @Override
    public int partition(String key, int numPartitions) {
        return 0;
    }
}