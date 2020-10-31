package com.cxp.springboot2kafka.simple.kafkaconsumer;

import com.cxp.springboot2kafka.constant.Topic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * 简单消息消费者
 * @author 程
 * @date 2019/7/29 下午5:50
 */
@Component
@Slf4j
public class KafkaSimpleConsumer {

    // 简单消费者
    @KafkaListener(id="simple_consumer1_1",groupId = "simpleGroup", topics = {Topic.SIMPLE})
    public void consumer1_1(ConsumerRecord<String, Object> record,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                            Consumer consumer) {
        log.info("======== consumer1_1 ========");
        System.out.println(String.format("消费者收到消息: %s ; topic: %s ; partition: %s ;offset: %s .",
                record.value(),topic,partition,record.offset()));
        System.out.println("消费者收到消息: topic :" + record.topic() );
        /*
         * 如果需要手工提交异步 consumer.commitSync();
         * 手工同步提交 consumer.commitAsync()
         */
    }

}
