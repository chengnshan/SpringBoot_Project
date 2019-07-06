package com.cxp.springboot2rabbitmq.rabbitConfig;

import com.cxp.springboot2rabbitmq.rabbitConsumer.pojo.UserInfo;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 程
 * @date 2019/7/1 下午8:17
 */
@Configuration
public class RabbitConfig {

    /**
     * 定义消息转换实例  转化成 JSON 传输  传输实体就可以不用实现序列化
     * 注意:如果发送方和接收方都用Jackson2JsonMessageConverter的,那么消费者在接收消息把json转换为对象时,消费者端对象的
     *      全路径名必须和生产者一致，否则会转换失败。我们发现生产者和消费者的耦合度太高,优化方法:
     *     参考:https://www.jianshu.com/p/83861676051c
     * @return
     */
    @Bean
    public MessageConverter integrationEventMessageConverter(){
        //指定Json转换器
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter();

        //消费端配置映射
        Map<String, Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("userInfo",UserInfo.class);

        DefaultJackson2JavaTypeMapper jackson2JavaTypeMapper = new DefaultJackson2JavaTypeMapper();
        jackson2JavaTypeMapper.setIdClassMapping(idClassMapping);
        jackson2JavaTypeMapper.setTrustedPackages("*");

        //在jackson2JsonMessageConverter转换器中指定映射配置
        jackson2JsonMessageConverter.setJavaTypeMapper(jackson2JavaTypeMapper);

        return jackson2JsonMessageConverter;
    }

}
