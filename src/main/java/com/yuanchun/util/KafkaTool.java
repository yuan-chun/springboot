package com.yuanchun.util;


import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.*;

/**
 * 修改offset
 */
public class KafkaTool {
    Properties props = new Properties();

    private void before() {

        props.put("bootstrap.servers", "kafka001:9091,kafka002:9092,kafka003:9093");
        props.put("group.id", "group-2");
        props.put("enable.auto.commit", "true");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }

    private String seek(String[] args) {
        String topic = args[0];
        int partition = Integer.parseInt(args[1]);
        int offset = Integer.parseInt(args[2]);
        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        consumer.assign(Arrays.asList(topicPartition));

        consumer.seek(new TopicPartition(topic, partition), offset);
        consumer.close();
        return "SUCC";
    }


    public void seek2(String[] args) {
        String topic = args[0];
        int offset = Integer.parseInt(args[2]);
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        KafkaConsumer<Integer, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Collections.singletonList(topic));
        Set<TopicPartition> li = consumer.assignment();
        //从所有分区的所有偏移量开始消费
        for (TopicPartition partition : consumer.assignment()) {
            consumer.seek(partition, offset);
        }
        /*
        //从特定分区的特定偏移量开始消费
        int partition = 0;
        TopicPartition topicPartition = new TopicPartition(topic, partition);
        consumer.seek(topicPartition, offset);
        */

        consumer.subscribe(Collections.singletonList(topic));
        while (true) {
            //如果缓冲区中没有数据会阻塞
            ConsumerRecords<Integer, String> records = consumer.poll(1000);
            for (ConsumerRecord<Integer, String> record : records) {
                System.out.println("Received message: (" + record.key() + ", " + record.value() + ") at offset " + record.offset());
            }
            consumer.commitSync();
        }

    }


    public static void main(String[] args) {
        args = new String[4];
        args[0] = "topic-2";
        args[1] = "0";
        args[2] = "100";
        KafkaTool kt = new KafkaTool();
        kt.before();
//        System.out.println(kt.seek(args));
        kt.seek2(args);


    }


}
