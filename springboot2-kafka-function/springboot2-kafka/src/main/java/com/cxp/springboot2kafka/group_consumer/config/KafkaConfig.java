package com.cxp.springboot2kafka.group_consumer.config;

import com.cxp.springboot2kafka.constant.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: liu yan
 * @create: 2019-07-30 13:27
 */
@Configuration
public class KafkaConfig {
    
    /**
    * @Description:
    * @Param: 
    * @return: 
    * @Author: cheng
    * @date: 2019/7/30
    */
   @Bean
   public NewTopic groupTopic(){
       // 指定主题名称，分区数量，和复制因子
       return new NewTopic(Topic.GROUP,10,(short) 2);
   }

}
