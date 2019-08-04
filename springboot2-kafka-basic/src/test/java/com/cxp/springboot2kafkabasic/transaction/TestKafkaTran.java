package com.cxp.springboot2kafkabasic.transaction;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-07-31 16:02
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestKafkaTran {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Test
    @Transactional
    public void testTransactionalAnnotation() throws InterruptedException {
        kafkaTemplate.send("spring.boot.kafka.simple", "test transactional annotation");
//        throw new RuntimeException("fail");
    }

    @Test
    public void testExecuteInTransaction() throws InterruptedException {
        kafkaTemplate.executeInTransaction(new KafkaOperations.OperationsCallback() {
            @Override
            public Object doInOperations(KafkaOperations kafkaOperations) {
                kafkaOperations.send("spring.boot.kafka.simple", "test executeInTransaction");
                throw new RuntimeException("fail");
            //    return true;
            }
        });
    }
}
