package com.cxp.springboot2kafka.java_api;

import com.cxp.springboot2kafka.constant.Topic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-07-30 21:26
 */
public class Producer {

    /**
    * 生产者配置
    */
     private static Map<String, Object> senderProps() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.111.134:9092");
        props.put(ProducerConfig.RETRIES_CONFIG, 0);
        props.put(ProducerConfig.BATCH_SIZE_CONFIG, 16384);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return props;
     }

    /**
     * 发送模板配置
     * @return
     */
    public static KafkaTemplate<String, Object> createTemplate() {
        Map<String, Object> senderProps = senderProps();
        ProducerFactory<String, Object> producerFactory = new DefaultKafkaProducerFactory<>(senderProps);
        KafkaTemplate<String, Object> kafkaTemplate = new KafkaTemplate<>(producerFactory);
        return kafkaTemplate;
    }

    public static void main(String[] args) {
        KafkaTemplate<String, Object> kafkaTemplate = createTemplate();
        kafkaTemplate.setDefaultTopic(Topic.SIMPLE);
        ListenableFuture<SendResult<String, Object>> future = kafkaTemplate.send(Topic.SIMPLE,
                "This is Java API 222!");
        future.addCallback(result->{
            System.out.println("发送结果:" + result.toString());
        },ex->{
            System.out.println("发送消息失败:" + ex.getMessage());
        });
        kafkaTemplate.flush();
    }
}
