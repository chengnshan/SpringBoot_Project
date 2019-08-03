package com.cxp.springboot2kafka.reply.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.TopicPartitionInitialOffset;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-07-30 17:05
 */
@Configuration
public class KafkaGlobalConfig {

    @Bean
    public ReplyingKafkaTemplate replyingKafkaTemplate(
            ProducerFactory producerFactory,
            KafkaMessageListenerContainer replyContainer){
        ReplyingKafkaTemplate replyingKafkaTemplate = new ReplyingKafkaTemplate<>(producerFactory,replyContainer);
        //超时时间,默认5000L
        replyingKafkaTemplate.setReplyTimeout(20000L);
        return replyingKafkaTemplate;
    }

    @Bean
    public KafkaMessageListenerContainer replyContainer(ConsumerFactory consumerFactory){

        TopicPartitionInitialOffset offset = new TopicPartitionInitialOffset(
                "spring.boot.kafka.reply.return",0);
        ContainerProperties containerProperties = new ContainerProperties(offset);
        containerProperties.setGroupId("replyReturnGroup");
        containerProperties.setClientId("replyReturn1");

        KafkaMessageListenerContainer container = new KafkaMessageListenerContainer(consumerFactory, containerProperties);
        return container;
    }
}
