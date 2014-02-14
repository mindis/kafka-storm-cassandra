package storm;

import java.util.Map;

import kafka.ConsumerClient;
import kafka.Metric.StorageRequest;
import kafka.consumer.ConsumerIterator;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;

/**
 * Spout起到和外界沟通的作用，他可以从一个数据库中按照某种规则取数据，也可以从分布式队列中取任务
 * 
 * @desc
 * @author WY 创建时间 2014年1月27日 下午4:35:15
 */
public class SimpleSpout extends BaseRichSpout {

    private static final long serialVersionUID = 1L;
    /** 发射数据的工具类 */
    private SpoutOutputCollector collector;

    private ConsumerClient consumer;

    private ConsumerIterator<String, StorageRequest> iterator;

    @Override
    public void open(Map conf, TopologyContext context, SpoutOutputCollector collector) {
        this.collector = collector;
        consumer = new ConsumerClient("C11_test");
        iterator = consumer.getIterator();
    }

    /**
     * 在SpoutTracker类中被调用，每调用一次就可以向storm集群中发射一条数据（一个tuple元组）， 该方法会被不停的调用
     */
    @Override
    public void nextTuple() {
        // 获取需要发送的数据源
        while (iterator.hasNext()) {
            collector.emit(new Values(iterator.next().message()));
        }
    }

    /**
     * 定义字段id，该id在简单模式下没有用处，但在按照字段分组的模式下有很大的用处。
     * 该declarer变量有很大作用，我们还可以调用declarer.
     * declareStream();来定义stramId，该id可以用来定义更加复杂的流拓扑结构
     */
    @Override
    public void declareOutputFields(OutputFieldsDeclarer declarer) {
        declarer.declare(new Fields("source"));
    }
}
