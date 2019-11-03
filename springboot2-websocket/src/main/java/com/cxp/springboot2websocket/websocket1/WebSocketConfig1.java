package com.cxp.springboot2websocket.websocket1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 *
 * @author : cheng
 * @date : 2019-11-02 20:52
 */
@Configuration
public class WebSocketConfig1 {

    /**
     * 注意，如果使用springboot内置的spring-boot-starter-tomcat跑，那么这个代码加上，
     *      如果是打war包放到服务器的tomcat上跑，就得把这段注掉
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
