package com.cxp.springboot2shardingsphere.pojo;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-20 22:36
 */
@Data
public class TbOrder {

    private Long orderId;
    private BigDecimal price;
    private Integer userId;
    private String status;
}
