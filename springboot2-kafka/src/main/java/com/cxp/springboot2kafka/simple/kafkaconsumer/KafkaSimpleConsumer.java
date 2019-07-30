package com.cxp.springboot2kafka.simple.kafkaconsumer;

import com.cxp.springboot2kafka.constant.Topic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
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
    @KafkaListener(groupId = "simpleGroup", topics = {Topic.SIMPLE})
    @KafkaHandler(isDefault = true)
    public void consumer1_1(ConsumerRecord<String, Object> record,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                            Consumer consumer) {
        System.out.println("消费者收到消息:" + record.value() + "; topic:" + topic);
        System.out.println("消费者收到消息: partition :" + record.partition() );
        System.out.println("消费者收到消息: topic :" + record.topic() );

        /*
         * 如果需要手工提交异步 consumer.commitSync();
         * 手工同步提交 consumer.commitAsync()
         */
    }

}
