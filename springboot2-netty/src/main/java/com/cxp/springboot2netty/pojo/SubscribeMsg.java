package com.cxp.springboot2netty.pojo;

import java.io.Serializable;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-08 10:11
 */
public class SubscribeMsg extends BaseMsg implements Serializable {

    private Integer subReqId;
    private String desc;

    public SubscribeMsg() {
    }

    public SubscribeMsg(Integer subReqId) {
        this.subReqId = subReqId;
    }

    public SubscribeMsg(Integer subReqId, String desc) {
        this.subReqId = subReqId;
        this.desc = desc;
    }

    public Integer getSubReqId() {
        return subReqId;
    }

    public void setSubReqId(Integer subReqId) {
        this.subReqId = subReqId;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "SubscribeMsg{" +
                "subReqId=" + subReqId +
                ", desc='" + desc + '\'' +
                '}';
    }

}
