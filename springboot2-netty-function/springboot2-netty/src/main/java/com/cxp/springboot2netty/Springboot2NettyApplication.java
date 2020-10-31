package com.cxp.springboot2netty;

import com.cxp.springboot2netty.netty.netty_server.NettyServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author cheng
 */
@SpringBootApplication
/*@ServletComponentScan*/
public class Springboot2NettyApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(Springboot2NettyApplication.class, args);
    }

    @Autowired
    private NettyServer nettyServer;

    @Override
    public void run(String... args) throws Exception {
        nettyServer.init();
        Runtime.getRuntime().addShutdownHook(new Thread(){
            @Override
            public void run() {

            }
        });
    }
}
