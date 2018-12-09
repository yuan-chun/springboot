package com.yuanchun.util.test;



import org.apache.hadoop.io.Text;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.PairFlatMapFunction;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.api.java.function.VoidFunction;
import scala.Tuple2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SparkRDDTest {
    public static void main(String[] args) {
        testRDDMap();  //不能改变元素数
//        testRDDFlatMap();  //可以改变元素数
//		testRDDMapToPair();  //不能改变元素数
//		testRDDFlatMapToPair();  //可以改变元素数
//		testRDDTextLoad();
//		testRDDLeftJoin();
//        org.jboss.netty.channel.socket.nio.WorkerPool
//        com.fasterxml.jackson.module.scala.ser.ScalaIteratorSerializer
    }

    public static void testRDDMap(){
        SparkConf conf = new SparkConf().setMaster("local").setAppName("LineCount");
        conf.set("spark.testing.memory", "2147480000");
        System.setProperty("hadoop.home.dir", "D:\\Sources\\hadoop-common-2.2.0-bin-master");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> lineList = Arrays.asList("hello xuruyun jsfjs" , "hello xuruyun yuanchun", "hello wangfei");

        JavaRDD<String> lines = sc.parallelize(lineList);


        //将每一行输入拼接 字符，count = 3 count不变
        JavaRDD<String> words = lines.map(
                new Function<String, String>() {

                    @Override
                    public String call(String line) throws Exception {
                        // TODO Auto-generated method stub
                        return line+"hhhhhhhhh";
                    }

                });
        System.out.println("==words.count=="+words.count());

        words.foreach(new VoidFunction<String>() {
            private static final long serialVersionUID = 1L;

            @Override
            public void call(String result) throws Exception {
                System.out.println(result);
            }
        });


        //将每一行输入转化为集合，新的rdd元素 为 集合String[]  count不变
        JavaRDD<String[]> words1 = lines.map(
                new Function<String, String[]>() {
                    @Override
                    public String[] call(String line) throws Exception {
                        // TODO Auto-generated method stub
                        return line.split(" ");
                    }
                });
        System.out.println("==words.count=="+words1.count());

        words1.foreach(new VoidFunction<String[]>() {
            @Override
            public void call(String[] t) throws Exception {
                System.out.println(t.length);
                for(String str:t){
                    System.out.println("==str=="+str);
                }
            }
        });
        sc.close();

    }


    public static void testRDDFlatMap(){
        SparkConf conf = new SparkConf().setAppName("LineCount").setMaster("local");
        conf.set("spark.testing.memory", "2147480000");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> lineList = Arrays.asList("hello xuruyun jsfjs" , "hello xuruyun yuanchun", "hello wangfei");

        //将每一行输入拼接 字符，count = 3 count不变
        JavaRDD<String> lines = sc.parallelize(lineList);


        // flatMap = flat + map
        // 将每一行数据处理返回一个集合，再将整个集合打碎，所有元素汇成一个大集合。count = 6 count变化
        JavaRDD<String> words = lines.flatMap(new FlatMapFunction<String, String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Iterable<String> call(String line) throws Exception {
                return Arrays.asList(line.split(" "));
            }
        });
        System.out.println("==words.count=="+words.count());

        words.foreach(new VoidFunction<String>() {

            private static final long serialVersionUID = 1L;

            @Override
            public void call(String result) throws Exception {
                System.out.println(result);
            }
        });

        sc.close();
    }


    public static void testRDDMapToPair(){
        SparkConf conf = new SparkConf().setAppName("LineCount").setMaster("local");
        conf.set("spark.testing.memory", "2147480000");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> lineList = Arrays.asList("hello xuruyun jsfjs" , "hello xuruyun yuanchun", "hello wangfei");

        //将每一行输入拼接 字符，count = 3 count不变
        JavaRDD<String> lines = sc.parallelize(lineList);


        JavaPairRDD<String,String> words = lines.mapToPair(
                new PairFunction() {
                    @Override
                    public Tuple2<String, String> call(Object object)
                            throws Exception {
                        String t = (String)object;
                        return new Tuple2<String, String>(t.split(" ")[0],t);
                    }
                });

        System.out.println("==words.count=="+words.count());
        words.foreach(new VoidFunction<Tuple2<String,String>>() {
            @Override
            public void call(Tuple2<String, String> t) throws Exception {
                System.out.println("===Tuple2.1==="+t);
            }
        });



//			JavaPairRDD<String,String> words1 = lines.mapToPair(
//					new PairFunction<String, String, String>() {
//
//						@Override
//						public Tuple2<String, String> call(String t) throws Exception {
//							return new Tuple2<String, String>(t.split(" ")[0],t);
//						}
//					});
//
//			System.out.println("==words1.count=="+words1.count());
//
//			words1.foreach(new VoidFunction<Tuple2<String,String>>() {
//
//						@Override
//						public void call(Tuple2<String, String> t) throws Exception {
//							System.out.println("===Tuple2.2==="+t);
//						}
//					});

        sc.close();
    }


    public static void testRDDFlatMapToPair(){
        SparkConf conf = new SparkConf().setAppName("LineCount").setMaster("local");
        conf.set("spark.testing.memory", "2147480000");
        JavaSparkContext sc = new JavaSparkContext(conf);

        List<String> lineList = Arrays.asList("hello xuruyun" , "hello xuruyun", "hello wangfei");

        //将每一行输入拼接 字符，count = 3 count不变
        JavaRDD<String> lines = sc.parallelize(lineList);


        JavaPairRDD<String,String> words = lines.flatMapToPair(
                new PairFlatMapFunction<String, String, String>() {
                    @Override
                    public Iterable<Tuple2<String, String>> call(String t)
                            throws Exception {
                        ArrayList<Tuple2<String, String>> list = new ArrayList<Tuple2<String, String>>();
                        String[] strs = t.split(" ");
                        for (int i = 0; i < strs.length; i++) {
                            list.add(new Tuple2<String, String>(strs[i], t));
                        }
                        return list;
                    }

                });
        System.out.println("==words.count=="+words.count());
        words.foreach(new VoidFunction<Tuple2<String,String>>() {
            @Override
            public void call(Tuple2<String, String> t) throws Exception {
                System.out.println("===Tuple2.1==="+t);
            }
        });

        sc.close();
    }


    public static void testRDDTextLoad(){
        SparkConf conf = new SparkConf().setAppName("LineCount").setMaster("local");
        conf.set("spark.testing.memory", "2147480000");
        JavaSparkContext sc = new JavaSparkContext(conf);

        //将每一行输入拼接 字符，count = 3 count不变
        JavaRDD<String> lines = sc.textFile("E:\\testFile\\testSpark.txt");

        //JavaRDD<String> lines1 = sc.textFile("E:\\testFile\\testSpark1.txt");



        JavaPairRDD<String,String> words = lines.flatMapToPair(
                new PairFlatMapFunction<String, String, String>() {
                    @Override
                    public Iterable<Tuple2<String, String>> call(String t)
                            throws Exception {
                        ArrayList<Tuple2<String, String>> list = new ArrayList<Tuple2<String, String>>();
                        String[] strs = t.split(" ");
                        for (int i = 0; i < strs.length; i++) {
                            list.add(new Tuple2<String, String>(strs[i], t));
                        }
                        return list;
                    }
                });

        JavaRDD<Text> textRDD = words.map(
                new Function<Tuple2<String,String>, Text>() {
                    @Override
                    public Text call(Tuple2<String, String> tuple)
                            throws Exception {
                        return new Text(tuple._1+tuple._2);
                    }
                });

        System.out.println("==words.count=="+words.count());
        //textRDD.saveAsTextFile("E:\\testFile\\testSpark1");
        words.foreach(new VoidFunction<Tuple2<String,String>>() {
            @Override
            public void call(Tuple2<String, String> t) throws Exception {
                System.out.println("===Tuple2.1==="+t);
            }
        });

        textRDD.foreach(new VoidFunction<Text>() {
            @Override
            public void call(Text t) throws Exception {
                System.out.println(t);
            }
        });

        sc.close();
    }


    public static void testRDDLeftJoin(){
        SparkConf conf = new SparkConf().setAppName("LineCount").setMaster("local");
        conf.set("spark.testing.memory", "2147480000");
        JavaSparkContext sc = new JavaSparkContext(conf);

        //将每一行输入拼接 字符，count = 3 count不变
        JavaRDD<String> lines = sc.textFile("E:\\testFile\\testSpark.txt",3);

        JavaRDD<String> lines1 = sc.textFile("E:\\testFile\\testSpark1.txt",3);

        //JavaRDD<String> union = lines.union(lines1); //合并，不去重
        //JavaRDD<String> union = lines.subtract(lines1); //完全相同时去除
//			union.foreach(new VoidFunction<String>() {
//				@Override
//				public void call(String t) throws Exception {
//					System.out.println(t);
//				}
//			});

        JavaPairRDD<String, String> linesPair = lines.mapToPair(
                new PairFunction<String, String, String>() {

                    @Override
                    public Tuple2<String, String> call(String t)
                            throws Exception {
                        return new Tuple2<String, String>(t.split(" ")[0],t);
                    }
                });

        JavaPairRDD<String, String> lines1Pair = lines1.mapToPair(
                new PairFunction<String, String, String>() {

                    @Override
                    public Tuple2<String, String> call(String t)
                            throws Exception {
                        return new Tuple2<String, String>(t.split(" ")[0],t);
                    }
                });
        // leftOuterJoin
//			JavaPairRDD<String, Tuple2<String, Optional<String>>> joinRDD = linesPair.leftOuterJoin(lines1Pair);
//
//			joinRDD.foreach(new VoidFunction<Tuple2<String,Tuple2<String,Optional<String>>>>() {
//
//				@Override
//				public void call(Tuple2<String, Tuple2<String, Optional<String>>> t)
//						throws Exception {
//					System.out.println("===leftOuterJoin==="+t._1+"="+t._2._1+t._2._2.toString());
//				}
//			});
        JavaPairRDD<String, String> interRDD = linesPair.intersection(lines1Pair);
        JavaPairRDD<String, Tuple2<Iterable<String>, Iterable<String>>> cogroupRDD =linesPair.cogroup(lines1Pair);
//			cogroupRDD.foreach(new VoidFunction<Tuple2<String,Tuple2<Iterable<String>,Iterable<String>>>>() {
//
//				@Override
//				public void call(Tuple2<String, Tuple2<Iterable<String>, Iterable<String>>> t)
//						throws Exception {
//					System.out.println("===cogroup==="+t._1);
//					System.out.println("===t._2==="+t._2);
//
//				}
//			});

        JavaPairRDD<String,String> resultRDD = cogroupRDD.mapToPair(
                new PairFunction<Tuple2<String,Tuple2<Iterable<String>,Iterable<String>>>, String, String>() {

                    @Override
                    public Tuple2<String, String> call(
                            Tuple2<String, Tuple2<Iterable<String>, Iterable<String>>> t)
                            throws Exception {
                        return new Tuple2<String, String>(t._1,t._2._1.iterator().next().toString());
                    }
                });
        resultRDD.foreach(new VoidFunction<Tuple2<String,String>>() {
            @Override
            public void call(Tuple2<String, String> t) throws Exception {
                System.out.println(""+t._2);
            }
        });

        sc.close();
    }

}
