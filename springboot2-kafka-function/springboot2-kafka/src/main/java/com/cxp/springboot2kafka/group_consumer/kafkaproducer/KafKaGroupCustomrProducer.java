package com.cxp.springboot2kafka.group_consumer.kafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * @author 程
 * @date 2019/7/29 下午5:49
 */
@Component
@Slf4j
public class KafKaGroupCustomrProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    /**
     *
     * @param topic
     * @param partition 指定分区
     * @param key   指定消息键   分区优先
     * @param object
     */
    public void sendMessage(String topic,Integer partition,Object key, Object object) {
        ListenableFuture future = kafkaTemplate.send(topic, partition, key, object);
        future.addCallback(new ListenableFutureCallback() {
            @Override
            public void onFailure(Throwable throwable) {
                log.info("发送消息失败:" + throwable.getMessage());
            }

            @Override
            public void onSuccess(Object sendResult) {
                System.out.println("发送结果:" + sendResult.toString());
            }
        });
    }

}
