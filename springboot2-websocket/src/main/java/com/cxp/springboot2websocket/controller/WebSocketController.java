package com.cxp.springboot2websocket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author 程
 * @date 2019/7/15 下午2:20
 */
@Controller
public class WebSocketController {

    @GetMapping(value = "/toWebSocketPage")
    public String toWebSocketPage(){
        return "websocket";
    }

    @GetMapping(value = "/toVueWebSocketPage")
    public String toVueWebSocketPage(){
        return "vue_websocket";
    }
}
