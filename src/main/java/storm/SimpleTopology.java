package storm;

import backtype.storm.Config;
import backtype.storm.StormSubmitter;
import backtype.storm.generated.AlreadyAliveException;
import backtype.storm.generated.InvalidTopologyException;
import backtype.storm.topology.TopologyBuilder;

/**
 * SimpleTopology类包含一个main函数，是Storm程序执行的入口点， 包括一个数据喷发节点spout和一个数据处理节点bolt。
 * 
 * @desc
 * @author WY 创建时间 2014年1月27日 下午4:53:35
 */
public class SimpleTopology {
    public static void main(String[] args) {
        startStormTopology(args[0]);
    }

    private static void startStormTopology(String topologyName) {
        TopologyBuilder builder = new TopologyBuilder();
        // 设置喷发节点并分配并发数，该并发数将会控制该对象在集群中的线程数。
        builder.setSpout("SimpleSpout", new SimpleSpout(), 1);
        // 设置数据处理节点并分配并发数。指定该节点接收喷发节点的策略为随机方式。
        builder.setBolt("SimpleBout", new SimpleBolt(), 1).shuffleGrouping("SimpleSpout");

        Config config = new Config();
        config.setDebug(true);
        config.setNumWorkers(1);
        // 本地模式
        // config.setMaxTaskParallelism(1);
        // LocalCluster cluster = new LocalCluster();
        // cluster.submitTopology("xxx", config, builder.createTopology());

        try {
            StormSubmitter.submitTopology(topologyName, config, builder.createTopology());
        } catch (AlreadyAliveException e) {
            e.printStackTrace();
        } catch (InvalidTopologyException e) {
            e.printStackTrace();
        }
    }
}
