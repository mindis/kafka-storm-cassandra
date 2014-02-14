package kafka.util;

import com.google.protobuf.InvalidProtocolBufferException;

import kafka.Metric.StorageRequest;
import kafka.serializer.Decoder;

public class MetricValueDecoder implements Decoder<StorageRequest> {

    @Override
    public StorageRequest fromBytes(byte[] metric) {
        try {
            return StorageRequest.parseFrom(metric);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
            return null;
        }
    }
}
