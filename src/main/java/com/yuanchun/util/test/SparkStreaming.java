package com.yuanchun.util.test;

import com.google.common.base.Optional;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.api.java.JavaStreamingContextFactory;
import scala.Serializable;
import scala.Tuple2;

import java.util.Arrays;
import java.util.List;

//nc -L -p 9999
public class SparkStreaming implements Serializable {

    public static void main(String[] args) {

        /*Function0<JavaStreamingContext> createContextFunc =
                () -> createContext() ;*/

        JavaStreamingContextFactory createContextFunc = new JavaStreamingContextFactory() {
            @Override
            public JavaStreamingContext create() {
                return createContext();
            }
        };


        //可恢复的streaming 当从checkpoint恢复时，即使改了程序也不生效，自己记录 offset
        String checkpoint = "G:\\testFile\\checkpointLocation";
        JavaStreamingContext ssc = JavaStreamingContext.getOrCreate(checkpoint,createContextFunc);

        ssc.start();
        ssc.awaitTermination();



    }

    //为Driver失败的恢复机制重写程序
    public static JavaStreamingContext createContext() {
        // Create the context with a 1 second batch size
        String checkpoint = "G:\\testFile\\checkpointLocation";
        SparkConf sparkConf = new SparkConf().setAppName("JavaNetworkWordCount").setMaster("local[2]");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(10));
        ssc.checkpoint(checkpoint);
        ssc.sparkContext().setLogLevel("ERROR");

        JavaReceiverInputDStream<String> lines = ssc.socketTextStream(
                "slave68", Integer.parseInt("8089"), StorageLevels.MEMORY_AND_DISK_SER);

        //window 滑动时间间隔等于滑动步长，和每个批次时间无关，可用于做某个时间窗口的实时数据显示，刷新时间为滑动步长
        JavaDStream<String> words = lines.window(Durations.seconds(20),Durations.seconds(20)).flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String x) {
                return Arrays.asList(x.split(" ")) ;
            }
        });

        /*JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String x) {
                return Arrays.asList(x.split(" ")) ;
            }
        });*/

        //reduceByKey
        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(
                new PairFunction<String, String, Integer>() {
                    @Override
                    public Tuple2<String, Integer> call(String s) {
                        return new Tuple2<String, Integer>(s, 1);
                    }
                }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer i1, Integer i2) {
                return i1 + i2;
            }
        });

        //updateStateByKey  用于保存之前的处理结果
        JavaPairDStream<String, Integer> pairs = words.mapToPair(new PairFunction<String, String, Integer>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Tuple2<String, Integer> call(String word) throws Exception {
                return new Tuple2<String, Integer>(word, 1);
            }
        });
        JavaPairDStream<String, Integer> wordcounts = pairs.updateStateByKey(new Function2<List<Integer>, Optional<Integer>, Optional<Integer>>() {

            private static final long serialVersionUID = 1L;

            // 实际上,对于每个单词,每次batch计算的时候,都会调用这个函数,第一个参数values相当于这个batch中
            // 这个key对应的新的一组值,可能有多个,可能2个1,(xuruyun,1)(xuruyun,1),那么这个values就是(1,1)
            // 那么第二个参数表示的是这个key之前的状态,我们看类型Integer你也就知道了,这里是泛型咱们自己指定的

            @Override
            public Optional<Integer> call(List<Integer> values,
                                          Optional<Integer> state) throws Exception {

                // Optional其实有两个子类,一个子类是Some,一个子类是None
                // 就是key有可能之前从来都没有出现过,意味着之前从来没有更新过状态
                Integer newValue = 0;
                if(state.isPresent()){
                    newValue = state.get();
                }
                for(Integer value : values){
                    System.out.println("value = "+value);
                    newValue += value;
                }

                return Optional.of(newValue);
            }
        });
                /*wordcounts.foreachRDD((v1, v2) -> {
                    v1.foreachPartitionAsync(r -> System.out.println("foreachPartitionAsync._1"+r.next()._1));
                } );
                wordcounts.foreachRDD((v1, v2) -> {
                    v1.foreachAsync(r -> System.out.println("foreachAsync._1"+r._1));
                } );*/
        wordCounts.print();
//        wordcounts.print();

        return ssc;
    }




}
