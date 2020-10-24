package com.cxp.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * @author : cheng
 * @date : 2020-06-21 19:58
 */
@ToString
@NoArgsConstructor
@Document(collection = "book")
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
    private String createTime;
    //修改时间
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8",locale = "zh")
    private String updateTime;

    public Book(String id, Integer price, @NonNull String name, String info, String publish, String createTime, String updateTime) {
        this.id = id;
        this.price = price;
        this.name = name;
        this.info = info;
        this.publish = publish;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPublish() {
        return publish;
    }

    public void setPublish(String publish) {
        this.publish = publish;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }
}
