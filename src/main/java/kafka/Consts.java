package kafka;

public interface Consts {

    public final static String GROUP_ID_VALUE = "1";

    public final static String KEY_SERIALIZER_CLASS_VALUE = "kafka.util.MetricKeyEncoder";

    public final static String VALUE_SERIALIZER_CLASS_VALUE = "kafka.util.MetricValueEncoder";

    public final static String QUEUE_MAX_MESSAGES_VALUE = "1000000";

    public final static String BATCH_NUM_MESSAGES_VALUE = "300";

    public final static String COMPRESSION_CODEC_VALUE = "gzip";

    public final static String QUEUE_MAX_TIEM_VALUE = "3000";

    public final static String PARTITIONER_CLASS_VALUE = "kafka.util.MetricPartitioner";
    // key
    public final static String ZK_CONNECT_KEY = "zookeeper.connect";

    public final static String GROUP_ID_KEY = "group.id";

    public final static String KEY_SERIALIZER_CLASS_KEY = "key.serializer.class";

    public final static String VALUE_SERIALIZER_CLASS_KEY = "serializer.class";

    public final static String METADATA_BROKER_LIST_KEY = "metadata.broker.list";

    public final static String AUTO_COMMIT_ENABLE_KEY = "auto.commit.enable";
    
    public final static String PARTITIONER_CLASS_KEY = "partitioner.class";

    public final static String QUEUE_MAX_MESSAGES_KEY = "queue.buffering.max.messages";

    public final static String PRODUCER_TYPE_KEY = "producer.type";

    public final static String BATCH_NUM_MESSAGES_KEY = "batch.num.messages";

    public final static String COMPRESSION_CODEC_KEY = "compression.codec";

    public final static String QUEUE_MAX_TIEM_KEY = "queue.buffering.max.ms";
}
