package com.cxp.springboot2rabbitmq.rabbitmq;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 程
 * @date 2019/7/5 下午5:40
 */
public class DLX_test {

    @Test
    public void producer() throws Exception{
        // 1. 创建连接工厂, 设置属性
        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("192.168.153.128");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 2. 创建连接
        Connection connection = factory.newConnection();

        // 3. 使用connection创建channel
        Channel channel = connection.createChannel();

        // 4. 声明死信队列Exchange和Queue
        channel.exchangeDeclare("dlx.exchange", "topic");
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");

        // 5. 声明普通Exchange
        String exchangeName = "test_dlx_exchange";
        String exchangeType = "topic";
        channel.exchangeDeclare(exchangeName, exchangeType, true, false, null);
        //普通Exchange与queue进行绑定
        String routingKey = "dlx.*";
        String queueName = "test_dlx_queue";

        // 6. 声明消息队列, 指定死信队列为dlx.exchange
        Map<String, Object> arguments = new HashMap<>();
        // x-dead-leeter-exchange属性用于指定死信队列为dlx.exchange
        arguments.put("x-dead-letter-exchange", "dlx.exchange");
        channel.queueDeclare(queueName, true, false, false, arguments);
        channel.queueBind(queueName,exchangeName,routingKey);

        // 发送消息
        routingKey = "dlx.qiye";

        String msg = "Hello, 七夜雪====消费超时时间5s";
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("5000").build();
        channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());
        msg = "Hello, 七夜雪==消费超时时间20s";
        properties = new AMQP.BasicProperties().builder().expiration("20000").build();
        channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());

        // 关闭连接
        channel.close();
        connection.close();

    }

    @Test
    public void consume() throws Exception{
        // 1. 创建连接工厂, 设置属性
        ConnectionFactory factory =new ConnectionFactory();
        factory.setHost("192.168.153.128");
        factory.setPort(5672);
        factory.setVirtualHost("/");
        factory.setUsername("admin");
        factory.setPassword("admin");
        // 2. 创建连接
        Connection connection = factory.newConnection();

        // 3. 使用connection创建channel
        Channel channel = connection.createChannel();

        String queueName = "test_dlx_queue";

        // 5. 创建消费者
        Consumer consumer = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv message: " + new String(body));
                // 获取head中内容
                System.out.println(properties.getHeaders()!= null ? properties.getHeaders().get("name"):"没有properties属性");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        // 6. 设置channel
        channel.basicConsume(queueName,false,consumer);
        // 等待回调函数执行完毕后，关闭资源
        TimeUnit.SECONDS.sleep(5);
        channel.close();
        connection.close();
    }
}
