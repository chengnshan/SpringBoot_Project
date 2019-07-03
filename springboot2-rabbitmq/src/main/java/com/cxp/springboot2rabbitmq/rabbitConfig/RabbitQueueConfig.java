package com.cxp.springboot2rabbitmq.rabbitConfig;

import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Broker:它提供一种传输服务,它的角色就是维护一条从生产者到消费者的路线，保证数据能按照指定的方式进行传输,
 * Exchange：消息交换机,它指定消息按什么规则,路由到哪个队列。
 * Queue:消息的载体,每个消息都会被投到一个或多个队列。
 * Binding:绑定，它的作用就是把exchange和queue按照路由规则绑定起来.
 * Routing Key:路由关键字,exchange根据这个关键字进行消息投递。
 * vhost:虚拟主机,一个broker里可以有多个vhost，用作不同用户的权限分离。
 * Producer:消息生产者,就是投递消息的程序.
 * Consumer:消息消费者,就是接受消息的程序.
 * Channel:消息通道,在客户端的每个连接里,可建立多个channel.
 *
 */
@Configuration
public class RabbitQueueConfig {

    @Autowired
    private RabbitProperties rabbitProperties;

    @Value(value = "${custom.rabbitmq.queue.direct}")
    private String directQueue;

    @Value(value = "${custom.rabbitmq.queue.topic1}")
    private String topicQueue1;

    @Value(value = "${custom.rabbitmq.queue.topic2}")
    private String topicQueue2;

    @Value(value = "${custom.rabbitmq.queue.headers}")
    private String headersQueue;

    @Bean(name = "directQueue")
    public Queue directQueue(){
        /**

         * name: 队列名称
         * durable：队列是否持久化.false:队列在内存中,服务器挂掉后,队列就没了;
         *                      true:服务器重启后,队列将会重新生成.注意:只是队列持久化,不代表队列中的消息持久化!!!!
         * exclusive：队列是否专属,专属的范围针对的是连接,也就是说,一个连接下面的多个信道是可见的.
         *              对于其他连接是不可见的.连接断开后,该队列会被删除.注意,不是信道断开,是连接断开.
         *              并且,就算设置成了持久化,也会删除.
         * autoDelete：true, 如果所有消费者都断开连接了,是否自动删除.如果还没有消费者从该队列获取过消息或者监听该队列,
         *              那么该队列不会删除.只有在有消费者从该队列获取过消息后,
         *              该队列才有可能自动删除(当所有消费者都断开连接,不管消息是否获取完)
         * arguments：队列的配置,一共10个::
         *              Message TTL : 消息生存周期,单位毫秒
         *              Auto expire : 队列多长时间(毫秒)没有被使用(访问)就会被删除.换个说法就是,当队列在指定的时间内没有被使用(访问)就会被删除.
         *              Max length : 队列可以容纳的消息的最大条数
         *              Max length bytes : 队列可以容纳的消息的最大字节数,超过这个字节数,队列头部的消息将会被丢弃
         *              Overflow behaviour : 队列中的消息溢出后如何处理
         *              Dead letter exchange : 溢出的消息需要发送到绑定该死信交换机的队列
         *              Dead letter routing key : 溢出的消息需要发送到绑定该死信交换机,并且路由键匹配的队列
         *              Maximum priority : 最大优先级
         *              Lazy mode : 懒人模式
         *              Master locator :集群相关设置
         * */
        return new Queue(directQueue,true,false,false,null);
    }

    @Bean(name = "topicQueue1")
    public Queue topicQueue1(){
        return new Queue(topicQueue1,true,false,false,null);
    }

    @Bean(name = "topicQueue2")
    public Queue topicQueue2(){
        return new Queue(topicQueue2,true,false,false,null);
    }

    @Bean(name = "headersQueue")
    public Queue headersQueue(){
        return new Queue(headersQueue,true,false,false,null);
    }
}
