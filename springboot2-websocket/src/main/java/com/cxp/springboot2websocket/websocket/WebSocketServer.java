package com.cxp.springboot2websocket.websocket;

import org.springframework.stereotype.Component;

import javax.websocket.OnOpen;
import javax.websocket.server.ServerEndpoint;

/**
 * @author 程
 * @date 2019/7/15 下午6:02
 */
@ServerEndpoint(value = "/websocket")
@Component
public class WebSocketServer {

    @OnOpen
    public void onOpen(){

    }
}
