package com.cxp.springboot2nettywebsocket;

import com.cxp.springboot2nettywebsocket.netty.WebSocketNettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Springboot2NettyWebsocketApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2NettyWebsocketApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        WebSocketNettyServer nettyServer = new WebSocketNettyServer();
        nettyServer.start();
    }
}
