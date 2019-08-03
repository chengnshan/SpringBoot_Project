package com.cxp.springboot2kafka.reply.config;

import com.cxp.springboot2kafka.constant.Topic;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: liu yan
 * @create: 2019-07-30 13:27
 */
@Configuration
public class ReplyKafkaConfig {
    
    /**
    * @Description:
    * @Param: 
    * @return: 
    * @Author: cheng
    * @date: 2019/7/30
    */
   @Bean
   public NewTopic replyTopic(){
       // 指定主题名称，分区数量，和复制因子
       return new NewTopic(Topic.REPLY,2,(short) 2);
   }

    @Bean
    public NewTopic replyReturnTopic(){
        // 指定主题名称，分区数量，和复制因子
        return new NewTopic(Topic.REPLYRETURN,2,(short) 2);
    }

}
