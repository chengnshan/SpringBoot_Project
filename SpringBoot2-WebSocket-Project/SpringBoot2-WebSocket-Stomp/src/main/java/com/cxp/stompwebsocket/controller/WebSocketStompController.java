package com.cxp.stompwebsocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-04-01 20:19
 */
@Controller
@Slf4j
@MessageMapping(value = "/wss")
public class WebSocketStompController {

    /**
     * 使用Restful风格
     * @param message
     * @param desVariable
     * @param headerMap
     * @return
     * @throws Exception
     */
    @MessageMapping("/hello/{variable}")
    //没有SendTo注解,默认是订阅了[/topic/wss/hello注解@MessageMapping组合]的客户端
    @SendTo(value = {"/topic/greetings","/queue/greetings"})
    public String greeting(String message, @DestinationVariable(value = "variable") String desVariable,
                           @Headers Map<String,Object> headerMap) throws Exception {
        log.info("WebSocketStompController == {} , desValriable : {}", message, desVariable);
        log.info( "{}",headerMap);
        return "message";
    }

    /**
     * 这里没用@SendTo注解指明消息目标接收者，消息将默认通过@SendTo("/topic/wss/twoWays")交给Broker进行处理
     * 不推荐不使用@SendTo注解指明目标接受者
     */
    @MessageMapping("/twoWays")
    @ResponseBody
    public String twoWays(String message, StompHeaderAccessor headerAccessor) {
        log.info( "twoWays : {}",message);
        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
        return "这是没有指明目标接受者的消息:"+ message;
    }


    private SimpMessagingTemplate template;

    @Autowired
    public void setTemplate(SimpMessagingTemplate template){
        this.template = template;
    }

    /**
     * 使用SimpMessagingTemplate发送消息给客户端
     * @param greeting
     */
    @RequestMapping(path = "/templateSend",method = RequestMethod.GET)
    @ResponseBody
    public void templateSend(String greeting){
        String text = "[" + System.currentTimeMillis() + "]:" + greeting;
        this.template.convertAndSend("/topic/greetings",text);
    }
}
