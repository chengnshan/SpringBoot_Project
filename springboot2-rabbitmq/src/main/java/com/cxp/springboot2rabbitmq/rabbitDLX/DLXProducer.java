package com.cxp.springboot2rabbitmq.rabbitDLX;

import com.cxp.springboot2rabbitmq.rabbitProducer.pojo.UserInfo;
import com.cxp.springboot2rabbitmq.utils.JackJsonUtil;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.UUID;

/**
 * @author 程
 * @date 2019/7/6 上午9:23
 */
@RestController
public class DLXProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 向普通队列发送消息
     * @param id
     */
    @RequestMapping(value = "/directSendDlx")
    public void directSendDlx(@RequestParam(value = "id",required = true) Integer id){
        UserInfo userInfo = new UserInfo(id,"张三丰","123456","男",
                "张真人",new Date());
        String strUserInfo = JackJsonUtil.objectToString(userInfo);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.getHeaders().put("__TypeId__","UserInfo");
        messageProperties.setContentType("application/json");
        Message message = new Message(strUserInfo.getBytes(),messageProperties);

        rabbitTemplate.convertAndSend(DLXConfig.REAL_EXCHANGE_NAME,DLXConfig.REAL_ROUTINGKEY,message,
                new CorrelationData(UUID.randomUUID().toString()));
    }
}
