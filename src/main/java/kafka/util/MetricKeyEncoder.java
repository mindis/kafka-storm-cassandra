package kafka.util;

import kafka.serializer.Encoder;
import kafka.utils.VerifiableProperties;

public class MetricKeyEncoder implements Encoder<String> {

    public MetricKeyEncoder(VerifiableProperties props) {
    }

    @Override
    public byte[] toBytes(String key) {
        return key.getBytes();
    }
}
