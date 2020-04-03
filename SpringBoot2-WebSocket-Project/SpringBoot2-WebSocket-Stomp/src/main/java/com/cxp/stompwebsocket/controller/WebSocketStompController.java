package com.cxp.stompwebsocket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-04-01 20:19
 */
@Controller
@Slf4j
@MessageMapping(value = "/wss")
public class WebSocketStompController {

    private SimpMessagingTemplate template;

    @Autowired
    public void setTemplate(SimpMessagingTemplate template){
        this.template = template;
    }

    @MessageMapping("/hello/{variable}")
    //没有SendTo注解,默认是订阅了[/topic/wss/hello注解@MessageMapping组合]的客户端
    @SendTo(value = {"/topic/greetings","/queue/greetings"})
    public String greeting(String message, @DestinationVariable(value = "variable") String desVariable,
                           @Headers Map<String,Object> headerMap) throws Exception {
        log.info("WebSocketStompController == {} , desValriable : {}", message, desVariable);
        log.info( "{}",headerMap);
        return "message";
    }

    @RequestMapping(path = "/templateSend",method = RequestMethod.GET)
    @ResponseBody
    public void templateSend(String greeting){
        String text = "[" + System.currentTimeMillis() + "]:" + greeting;
        this.template.convertAndSend("/topic/greetings",text);
    }
}
