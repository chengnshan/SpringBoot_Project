package com.cxp.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author : cheng
 * @date : 2020-06-21 19:58
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    private String id;
    //价格
    private Integer price;
    //书名
    @NonNull
    private String name;
    //简介
    private String info;
    //出版社
    private String publish;
    //创建时间
    private Date createTime;
    //修改时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8",locale = "zh")
    private Date updateTime;
}
