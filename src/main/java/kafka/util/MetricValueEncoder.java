package kafka.util;

import kafka.Metric.StorageRequest;
import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

public class MetricValueEncoder implements Encoder<StorageRequest> {

    public MetricValueEncoder(VerifiableProperties props) {
    }

    @Override
    public byte[] toBytes(StorageRequest metric) {
        return metric.toByteArray();
    }
}