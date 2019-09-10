package com.yuanchun.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * @ClassName Producer
 * @Author yuanchun
 * @Description //TODO $
 * @Date $ $
 **/
@RestController
public class Producer {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private KafkaTemplate kafkaTemplate;
    private int i = 0;
    private long sleep = 1000 * 60;
    private boolean send = false;

    @GetMapping("/kafka/isSend")
    public void idSend(String isSend) {
        this.send = "true".equals(isSend) ? true : false;
        logger.info("this.send=" + this.send);
    }


    @PostConstruct
    public void task() {
        logger.info("Kafka-produce-task start");
        Runnable t = () -> {
            while (true) {
                if (send) {
                    i++;
                    sendKafka(String.valueOf(i));
                    if (i % 1000 == 0) {
                        try {
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        };

        new Thread(t, "Kafka-produce-Task").start();
    }

    public void sendKafka(String message) {
        try {
            kafkaTemplate.send("topic-3-r2p1", message, message);
            logger.info("发送kafka成功.");
        } catch (Exception e) {
            logger.error("发送kafka失败", e);
        }
    }

}
