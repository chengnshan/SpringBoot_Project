package com.cxp.business.pojo;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author : cheng
 * @date : 2020-08-18 22:05
 */
@Data
@ToString
public class UserInfo {

    private String userName;
    private Integer age;
    private String address;
    private Date birthday;
}
