package com.cxp.springboot2rabbitmq.rabbitProducer.producer;

import com.cxp.springboot2rabbitmq.rabbitProducer.pojo.UserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * @author 程
 * @date 2019/7/1 下午5:52
 */
@RestController
public class RabbitProducerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RabbitProducerController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Value(value = "${custom.rabbitmq.exchange.direct}")
    private String directExchange;

    @Value(value = "${custom.rabbitmq.exchange.topic}")
    private String topicExchange;

    @Value(value = "${custom.rabbitmq.directRoutingKey}")
    private String directRoutingKey;

    @Value(value = "${custom.rabbitmq.topicRoutingKey1}")
    private String topicRoutingKey1;

    @Value(value = "${custom.rabbitmq.topicRoutingKey2}")
    private String topicRoutingKey2;

    private static Integer num = 0;

    /**
     * 发送Direct类型消息(单个对象发送)
     */
    @RequestMapping(value = "/directSend")
    public void directSend(){
        UserInfo userInfo = new UserInfo(2,"张三丰","123456","男",
                "张真人",new Date());

        Object receive = rabbitTemplate.convertSendAndReceive(directExchange, directRoutingKey, userInfo,
                message -> {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.getHeaders().put("__TypeId__","userInfo");
                    messageProperties.setContentType("application/json");
                    return message;
                },
                new CorrelationData(UUID.randomUUID().toString()));
        System.out.println("directSend : " + receive);
    }

    /**
     * 发送List对象集合消息,需要在headers中指定两个键值对：
     *      __TypeId__              表示要映射的类型
     *      __ContentTypeId__       表示集合中要映射的对象
     */
    @RequestMapping(value = "/directSendList")
    public void directSendList(){
        List<UserInfo> list = new ArrayList<>();
        list.add(new UserInfo(21,"张三丰","123456","男",
                "张真人",new Date()));
        list.add(new UserInfo(22,"张无忌","123456","男",
                "张无忌",new Date()));
        Object receive = rabbitTemplate.convertSendAndReceive(directExchange, directRoutingKey, list,
                message -> {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setContentType("application/json");
                    messageProperties.getHeaders().put("__TypeId__","java.util.List");
                    messageProperties.getHeaders().put("__ContentTypeId__","userInfo");
                    return message;
                },
                new CorrelationData(UUID.randomUUID().toString()));
        System.out.println("directSend : " + receive);
    }

    /**
     * 发送Map对象集合消息,需要在headers中指定两个键值对：
     *      __TypeId__              表示要映射的类型
     *      __ContentTypeId__       表示集合中要映射的对象
     */
    @RequestMapping(value = "/directSendMap")
    public String directSendMap(){
        Map<String,Object> map = new HashMap<>();
        map.put("userInfo1",new UserInfo(21,"张三丰","123456","男",
                "张真人",new Date()));
        map.put("userInfo2",new UserInfo(22,"张无忌","123456","男",
                "张无忌",new Date()));
        Object receive = rabbitTemplate.convertSendAndReceive(directExchange, directRoutingKey, map,
                message -> {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setContentType("application/json");
                    messageProperties.getHeaders().put("__TypeId__","java.util.Map");
                    messageProperties.getHeaders().put("__KeyTypeId__","java.lang.String");
                    messageProperties.getHeaders().put("__ContentTypeId__","userInfo");
                    return message;
                },
                new CorrelationData(UUID.randomUUID().toString()));
        System.out.println("directSend : " + receive);
        return "发送map成功";
    }

    /**
     * 此方法发送的消息会匹配到队列topicQueue1(路由键key.topic)和topicQueue2(路由键key.#)这两个队列
     */
    @RequestMapping(value = "/topicSend1")
    public void topicSend1(){
        UserInfo userInfo = new UserInfo(2,"李世民","123456","男",
                "唐太宗",new Date());
        Object receive = rabbitTemplate.convertSendAndReceive(topicExchange, topicRoutingKey1, userInfo,
                new CorrelationData(UUID.randomUUID().toString()));
        System.out.println("topicSend1 : " + receive);
    }

    /**
     * 此方法发送的消息会匹配到队列topicQueue2(路由键key.#)这两个队列
     */
    @RequestMapping(value = "/topicSend2")
    public void topicSend2(){
        UserInfo userInfo = null;
        for (int i =0;i<4;i++){
            userInfo = new UserInfo(i,"王五","123456","男",
                    "张真人",new Date());
            rabbitTemplate.convertAndSend(topicExchange, topicRoutingKey2, userInfo,
                    new CorrelationData(UUID.randomUUID().toString()));

        }
    }

    @RequestMapping(value = "/headerSend")
    public void headerSend(){
        UserInfo userInfo = new UserInfo(2,"header","123456","男",
                "header",new Date());

        rabbitTemplate.convertAndSend("headersExchange", "",
                userInfo,
                message -> {
                    message.getMessageProperties().setHeader("x-headers","header_value");
                    return message;
                },
                new CorrelationData(UUID.randomUUID().toString()));
    }

    @RequestMapping(value = "/fanoutSend")
    public void fanoutSend(){
        UserInfo userInfo = new UserInfo(num++,"fanout","fanout","男",
                "fanout",new Date());

        Object receive = rabbitTemplate.convertSendAndReceive("fanoutExchange", null, userInfo,
                new CorrelationData(UUID.randomUUID().toString()));
        System.out.println(receive);
    }
}
