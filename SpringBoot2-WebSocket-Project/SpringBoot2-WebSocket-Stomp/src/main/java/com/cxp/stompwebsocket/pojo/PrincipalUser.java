package com.cxp.stompwebsocket.pojo;

import java.security.Principal;

/**
 * @author : cheng
 * @date : 2020-04-11 18:44
 */
public class PrincipalUser implements Principal {

    private String userName;

    public PrincipalUser() {

    }

    public PrincipalUser(String userName) {
        this.userName = userName;
    }

    @Override
    public String getName() {
        return userName;
    }
}
