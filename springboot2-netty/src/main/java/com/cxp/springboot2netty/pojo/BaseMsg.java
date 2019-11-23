package com.cxp.springboot2netty.pojo;

import java.io.Serializable;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-08 09:03
 */
public abstract class BaseMsg implements Serializable{

    private static final long serialVersionUID = -4632810475600894253L;

    private MsgType msgType;
    private String clientID;

    public BaseMsg() {

    }

    public MsgType getMsgType() {
        return msgType;
    }

    public void setMsgType(MsgType msgType) {
        this.msgType = msgType;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

}
