package com.yuanchun.util.test;

import scala.Tuple2;
import com.google.common.collect.Lists;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import java.util.regex.Pattern;

//nc -L -p 9999
public final class SparkStreaming {
    private static final Pattern SPACE = Pattern.compile(" ");

    public static void main(String[] args) {
        // Create the context with a 1 second batch size
        SparkConf sparkConf = new SparkConf().setAppName("JavaNetworkWordCount").setMaster("local[2]");
        JavaStreamingContext ssc = new JavaStreamingContext(sparkConf, Durations.seconds(5));
//        ssc.sparkContext().setLogLevel("ERROR");
//        Logger.getLogger("org").setLevel(Level.ERROR);
        JavaReceiverInputDStream<String> lines = ssc.socketTextStream(
                "130.51.23.139", Integer.parseInt("9999"), StorageLevels.MEMORY_AND_DISK_SER);
        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            @Override
            public Iterable<String> call(String x) {
                return Lists.newArrayList(SPACE.split(x));
            }
        });
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

        wordCounts.print();
        ssc.start();
        ssc.awaitTermination();
    }
}
