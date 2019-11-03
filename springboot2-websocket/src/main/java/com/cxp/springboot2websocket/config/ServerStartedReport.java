package com.cxp.springboot2websocket.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author : cheng
 * @date : 2019-11-02 18:19
 */
@Component
@Order(1)
public class ServerStartedReport implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        System.out.println("=====应用已经成功启动====="+ Arrays.asList(args));
    }
}
