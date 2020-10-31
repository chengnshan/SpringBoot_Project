package com.cxp.springboot2rabbitmq.rabbitProducer.producer;

import com.cxp.springboot2rabbitmq.rabbitProducer.pojo.UserInfo;
import com.cxp.springboot2rabbitmq.utils.JackJsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * @author 程
 * @date 2019/7/9 下午12:03
 */
@RestController
public class DirectProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectProducer.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Value(value = "${custom.rabbitmq.exchange.direct}")
    private String directExchange;

    @Value(value = "${custom.rabbitmq.directRoutingKey}")
    private String directRoutingKey;

    /**
     * 发送Direct类型消息(单个对象发送)
     */
    @RequestMapping(value = "/directSend")
    public void directSend(){
        UserInfo userInfo = new UserInfo(2,"张三丰","123456","男",
                "张真人",new Date());
        String strUserInfo = JackJsonUtil.objectToString(userInfo);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("__TypeId__","UserInfo");
        messageProperties.setContentType("application/json");
        Message message = new Message(strUserInfo.getBytes(),messageProperties);
        Object receive = rabbitTemplate.convertSendAndReceive(directExchange, directRoutingKey,
                message, new CorrelationData(UUID.randomUUID().toString()));
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
        //list转换为json字符串
        String strJsonList = JackJsonUtil.objectToString(list);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__","java.util.List");
        messageProperties.getHeaders().put("__ContentTypeId__","UserInfo");
        Message message = new Message(strJsonList.getBytes(),messageProperties);

        Object receive = rabbitTemplate.convertSendAndReceive(directExchange, directRoutingKey, message,
                new CorrelationData(UUID.randomUUID().toString()));
        LOGGER.info("directSend : " + receive);
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

        String strMap = JackJsonUtil.objectToString(map);

        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("application/json");
        messageProperties.getHeaders().put("__TypeId__","java.util.Map");
        messageProperties.getHeaders().put("__KeyTypeId__","java.lang.String");
        messageProperties.getHeaders().put("__ContentTypeId__","UserInfo");

        Message message = new Message(strMap.getBytes(),messageProperties);
        Object receive = rabbitTemplate.convertSendAndReceive(directExchange, directRoutingKey, message,
                new CorrelationData(UUID.randomUUID().toString()));

        System.out.println("directSend : " + receive);
        return "发送map成功";
    }

    @RequestMapping(value = "/directSendJPG")
    public String directSendJPG(){

        byte[] bytes = new byte[0];
        try {
            bytes = Files.readAllBytes(Paths.get("/Volumes/Passport_2/study_practice_file/images/from/timg2.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType("image/jpg");
        Message message = new Message(bytes,messageProperties);

        Object receive = rabbitTemplate.convertSendAndReceive(directExchange, directRoutingKey,
                message, new CorrelationData(UUID.randomUUID().toString()));

        return String.valueOf(receive);
    }
}
