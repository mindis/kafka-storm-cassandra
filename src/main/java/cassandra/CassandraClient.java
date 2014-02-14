package cassandra;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Random;

import kafka.Metric.StorageRequest;
import kafka.Metric.StorageRequest.Builder;

import org.apache.cassandra.thrift.Cassandra;
import org.apache.cassandra.thrift.Cassandra.Client;
import org.apache.cassandra.thrift.Column;
import org.apache.cassandra.thrift.ColumnParent;
import org.apache.cassandra.thrift.ConsistencyLevel;
import org.apache.cassandra.thrift.InvalidRequestException;
import org.apache.cassandra.thrift.TimedOutException;
import org.apache.cassandra.thrift.UnavailableException;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFastFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

public class CassandraClient {

    TTransport tr = null;
    TBinaryProtocol protocol = null;
    Client client = null;
    ColumnParent parent = null;

    public CassandraClient() {
        tr = new TFastFramedTransport(new TSocket("192.168.198.129", 9160));
        protocol = new TBinaryProtocol(tr);
        client = new Cassandra.Client(protocol);
    }

    public void openClient() {
        try {
            if (!tr.isOpen()) {
                tr.open();
            }
        } catch (TTransportException e) {
            e.printStackTrace();
        }
    }

    public void closeClient() {
        if (tr.isOpen()) {
            tr.close();
        }
    }

    public void insertData(final StorageRequest lastMetric) {
        try {
            Column metricValue = new Column(toByteBuffer("value"));
            metricValue.setValue(toByteBuffer(lastMetric.getValue() + ""));
            metricValue.setTimestamp(lastMetric.getTimestamp());

            Column metricTime = new Column(toByteBuffer("time"));
            metricTime.setValue(toByteBuffer(lastMetric.getTimestamp() + ""));
            metricTime.setTimestamp(lastMetric.getTimestamp());
            client.insert(toByteBuffer(lastMetric.getName()), parent, metricValue,
                    ConsistencyLevel.ONE);
            client.insert(toByteBuffer(lastMetric.getName()), parent, metricTime,
                    ConsistencyLevel.ONE);
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (UnavailableException e) {
            e.printStackTrace();
        } catch (TimedOutException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }

    }

    public void useKeySpace(String keySpace) {
        try {
            client.set_keyspace(keySpace);
        } catch (InvalidRequestException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
        }
        parent = new ColumnParent("metric");// column family
    }

    /*
     * 将String转换为bytebuffer，以便插入cassandra
     */
    public static ByteBuffer toByteBuffer(String value) {
        try {
            return ByteBuffer.wrap(value.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }

    /*
     * 将bytebuffer转换为String
     */
    public static String toString(ByteBuffer buffer) throws UnsupportedEncodingException {
        byte[] bytes = new byte[buffer.remaining()];
        buffer.get(bytes);
        return new String(bytes, "UTF-8");
    }

    public static final StorageRequest createTestData(String metric) {
        Builder m = StorageRequest.newBuilder();
        m.setName(metric);
        m.setTimestamp(System.currentTimeMillis() / 1000);
        m.setValue(new Random().nextInt(1));
        return m.build();
    }

    public static void main(String args[]) {
        CassandraClient client = new CassandraClient();
        client.openClient();
        client.useKeySpace("kafka");
        StorageRequest lastMetric = createTestData("metric3");
        client.insertData(lastMetric);
        // client.closeClient();
    }
}
