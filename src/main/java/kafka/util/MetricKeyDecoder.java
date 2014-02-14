package kafka.util;

import kafka.serializer.Decoder;

public class MetricKeyDecoder implements Decoder<String> {

    @Override
    public String fromBytes(byte[] key) {
        return new String(key);
    }
}
