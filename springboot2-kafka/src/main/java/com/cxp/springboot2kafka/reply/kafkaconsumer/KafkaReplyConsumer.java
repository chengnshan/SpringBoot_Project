package com.cxp.springboot2kafka.reply.kafkaconsumer;

import com.cxp.springboot2kafka.constant.Topic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
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
public class KafkaReplyConsumer {

    // 简单消费者
    @KafkaListener(id="reply_consumer1_1",groupId = "replyGroup",
            topicPartitions = {@TopicPartition(topic = Topic.REPLY,partitions = "0")})
    @SendTo
    public String consumer1_1(ConsumerRecord<String, Object> record,
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
        return "This is return consumer1_1";
    }

    // 简单消费者
    @KafkaListener(id="reply_consumer1_2",groupId = "replyGroup",
            topicPartitions = {@TopicPartition(topic = Topic.REPLY,partitions = "1")})
    @KafkaHandler
    @SendTo(value = "spring.boot.kafka.reply.return")
    public String consumer1_2(ConsumerRecord<String, Object> record,
                              @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                              @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                              Consumer consumer) {
        log.info("======== consumer1_2 ========");
        System.out.println(String.format("消费者收到消息: %s ; topic: %s ; partition: %s ;offset: %s .",
                record.value(),topic,partition,record.offset()));
        System.out.println("消费者收到消息: topic :" + record.topic() );

        /*
         * 如果需要手工提交异步 consumer.commitSync();
         * 手工同步提交 consumer.commitAsync()
         */

        return "This is return consumer1_2";
    }


    @KafkaListener(id = "replyReturn2",groupId = "replyReturnGroup",
            topicPartitions = {@TopicPartition(topic = Topic.REPLYRETURN,partitions = "0")})
    public void replyReturn2(ConsumerRecord<String, Object> record,
                             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                             Consumer consumer){
        log.info("======== replyReturn2 ========");
        System.out.println(String.format("消费者收到消息: %s ; topic: %s ; partition: %s ;offset: %s .",
                record.value(),topic,partition,record.offset()));
        System.out.println("消费者收到消息: topic :" + record.topic() );
    }

    @KafkaListener(id = "replyReturn1",groupId = "replyReturnGroup",
            topicPartitions = {@TopicPartition(topic = Topic.REPLYRETURN,partitions = "1")})
    public void replyReturn1(ConsumerRecord<String, Object> record,
                             @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                             @Header(KafkaHeaders.RECEIVED_PARTITION_ID) Integer partition,
                             Consumer consumer){
        log.info("======== replyReturn1 ========");
        System.out.println(String.format("消费者收到消息: %s ; topic: %s ; partition: %s ;offset: %s .",
                record.value(),topic,partition,record.offset()));
        System.out.println("消费者收到消息: topic :" + record.topic() );
    }
}
