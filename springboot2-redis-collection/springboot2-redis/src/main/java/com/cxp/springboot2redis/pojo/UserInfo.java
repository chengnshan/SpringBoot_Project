package com.cxp.springboot2redis.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 程
 * @date 2019/6/23 下午4:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 439145555398089120L;

    private Integer id;
    private String userName;
    private String passWord;
    private String userSex;
    private String nickName;
    private Date birthday;

}
