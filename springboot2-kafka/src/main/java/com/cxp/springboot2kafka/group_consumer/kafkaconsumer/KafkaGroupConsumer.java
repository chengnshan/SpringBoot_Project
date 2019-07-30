package com.cxp.springboot2kafka.group_consumer.kafkaconsumer;

import com.cxp.springboot2kafka.constant.Topic;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 多个消费者群组可以共同读取同一个主题，彼此之间互不影响。
 *
 * 消费者1-1 监听主题的 0、1 分区
 * 消费者1-2 监听主题的 2、3 分区
 * 消费者1-3 监听主题的 0、1 分区
 * 消费者2-1 监听主题的所有分区
 *
 * @author 程
 * @date 2019/7/29 下午5:50
 */
@Component
@Slf4j
public class KafkaGroupConsumer {

    /**
     * 分组1 中的消费者1
     * @param record
     * @param topic
     * @param consumer
     */
    @KafkaListener(id = "consumer1-1",groupId = "group1", topicPartitions = {
            @TopicPartition(topic = Topic.GROUP,partitions = {"0","1"})
    })
    public void consumer1_1(ConsumerRecord<String, Object> record,
                            @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
                            Consumer consumer) {
        System.out.println("consumer1-1 收到消息:" + record.value());
    }

    /**
     * 分组1 中的消费者2
     * */
    @KafkaListener(id = "consumer1-2", groupId = "group1", topicPartitions =
            {@TopicPartition(topic = Topic.GROUP, partitions = {"2"})
            })
    public void consumer1_2(ConsumerRecord<String, Object> record) {
        System.out.println("consumer1-2 收到消息:" + record.value());
    }

    /**
    * 分组1 中的消费者3
    */
    @KafkaListener(id = "consumer1-3", groupId = "group1", topicPartitions =
            {@TopicPartition(topic = Topic.GROUP, partitions = {"3"})
            })
    public void consumer1_3(ConsumerRecord<String, Object> record) {
        System.out.println("consumer1-3 收到消息:" + record.value());
    }

    // 分组2 中的消费者
    @KafkaListener(id = "consumer2-1", groupId = "group2", topics = {Topic.GROUP,Topic.SIMPLE})
    public void consumer2_1(ConsumerRecord<String, Object> record) {
        System.err.println("consumer2-1 收到消息:" + record.value());
        try {
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
