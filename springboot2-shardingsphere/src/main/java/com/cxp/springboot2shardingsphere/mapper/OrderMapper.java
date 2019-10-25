package com.cxp.springboot2shardingsphere.mapper;

import com.cxp.springboot2shardingsphere.pojo.TbOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-20 22:34
 */
@Mapper
@Component
public interface OrderMapper {

    @Insert("insert into t_order(price,user_id,status ) values (#{price},#{userId},#{status})")
    int insertOrder(@Param("price")BigDecimal price,@Param("userId")Long userId,@Param("status")String status);

    @Select("<script>" +
            "select * from t_order t " +
            "where t.order_id in " +
            "<foreach collection='orderIds' open='(' close=')' item='id' separator=','>" +
            " #{id} " +
            "</foreach> " +
            "</script>")
    List<TbOrder> listByProperty(@Param("orderIds") List<Long> orderIds);
}
