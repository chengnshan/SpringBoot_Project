package com.cxp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author cheng
 * Hello world!
 */
@SpringBootApplication
public class MongodbBasicApp {
    public static void main(String[] args) {
        SpringApplication.run(MongodbBasicApp.class);
    }
}
