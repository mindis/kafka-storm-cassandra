package storm;

import java.util.List;
import java.util.Map;

import kafka.Metric.StorageRequest;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Tuple;
import cassandra.CassandraClient;

/**
 * 接收喷发节点(Spout)发送的数据进行简单的处理后，发射出去。
 * 
 * @desc
 * @author WY 创建时间 2014年1月27日 下午4:44:53
 */
public class SimpleBolt extends BaseBasicBolt {

    private static final long serialVersionUID = 1L;

    CassandraClient client = null;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        client = new CassandraClient();
        client.openClient();
        client.useKeySpace("kafka");
    }

    @Override
    public void execute(Tuple input, BasicOutputCollector collector) {
        List<Object> msgs = input.getValues();
        if(msgs!=null&&msgs.size()>0){
            // 加工收到的数据，然后发射出去
            for (int i = 0; i < msgs.size(); i++) {
                StorageRequest lastMetric = (StorageRequest) msgs.get(i);
                client.insertData(lastMetric);
            }
        }
    }
    
    @Override
    public void cleanup() {
        client.closeClient();
    }

    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("info"));
    }
}
