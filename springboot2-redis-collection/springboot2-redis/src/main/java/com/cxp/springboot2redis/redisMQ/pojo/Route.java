package com.cxp.springboot2redis.redisMQ.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * * <p>消息路由器，主要控制将消息从指定的队列路由到待消费的list<br>
 *  * 通过这种方式实现自定义延迟以及优先级发送</p>
 * @author 程
 * @date 2019/7/10 下午2:57
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Route implements Serializable {

    private static final long serialVersionUID = -3636877755876283942L;
    /**
     * 存放消息的队列
     */
    private String queue;

    /**
     * 待消费的列表
     */
    private String list;
}
