package com.yuanchun.util;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName Consumer
 * @Author yuanchun
 * @Description //TODO $
 * @Date $ $
 **/
@RestController
public class Consumer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    //    @KafkaListener(
//            topics = {"topic-1"},
//            topicPartitions = {
//                @TopicPartition(topic = "topic-1",partitions = {"1"}
//                   // , partitionOffsets = @PartitionOffset(partition = "1",initialOffset = "100")
//                )
//    })
//    @KafkaListener(topicPattern = "topic-.*")
//    @KafkaListener(topicPattern = "topic-2")
//    @KafkaListener(topics = {"topic-3-r2p1"})
    public void listen(ConsumerRecord<?, ?> record) {
        logger.info("kafka消费的key: " + record.offset());
        logger.info("kafka消费的value: " + record.value().toString());
    }



}
