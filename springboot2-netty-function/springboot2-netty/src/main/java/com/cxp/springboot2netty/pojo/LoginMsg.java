package com.cxp.springboot2netty.pojo;

import java.io.Serializable;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-08 09:11
 */
public class LoginMsg extends BaseMsg implements Serializable{

    private static final long serialVersionUID = 6672093717291823764L;

    String username;

    String password;

    public LoginMsg() {
        super();
        setMsgType(MsgType.LOGIN);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
