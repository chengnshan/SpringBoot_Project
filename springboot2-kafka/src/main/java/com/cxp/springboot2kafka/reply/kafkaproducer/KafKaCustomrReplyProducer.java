package com.cxp.springboot2kafka.reply.kafkaproducer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author 程
 * @date 2019/7/29 下午5:49
 */
@Component
@Slf4j
public class KafKaCustomrReplyProducer<K,V,R> {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private ReplyingKafkaTemplate replyingKafkaTemplate;

    public R sendAndReceive(ProducerRecord<K,V> producerRecord) {
        /*
         * 使用replyingKafkaTemplate.sendAndReceive()方法发送消息，该方法返回一个Future类RequestReplyFuture，这里类里面
         * 包含了获取发送结果的Future类和获取返回结果的Future类。使用replyingKafkaTemplate发送及返回都是异步操作。
         *
         * 调用RequestReplyFuture.getSendFutrue().get()方法可以获取到发送结果
         *
         * 调用RequestReplyFuture.get()方法可以获取到响应结果
         *
         */
        RequestReplyFuture<K,V,R> replyFuture = replyingKafkaTemplate.sendAndReceive(producerRecord);

        ListenableFuture<SendResult<K, V>> sendFuture = replyFuture.getSendFuture();
        sendFuture.addCallback(result -> {
            log.info("发送消息成功:" + result.toString());
        },ex -> {
            log.info("发送消息失败:" + ex.getMessage());
        });

        R returnResult = null;
        try {
            ConsumerRecord<K,R> consumerRecord = replyFuture.get(5, TimeUnit.SECONDS);
            returnResult = consumerRecord.value();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return returnResult;
    }

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
