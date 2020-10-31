package com.cxp.springboot2rabbitmq.rabbitProducer.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 程
 * @date 2019/6/23 下午4:43
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo implements Serializable {

    private static final long serialVersionUID = 439145555398089120L;

    private Integer id;
    private String userName;
    private String passWord;
    private String userSex;
    private String nickName;
    @JsonFormat(pattern = "YYYY-MM-dd HH:mm:ss")
    private Date birthday;
    private String jobs;

    public UserInfo(Integer id, String userName, String passWord, String userSex, String nickName, Date birthday){
        this.id = id;
        this.userName = userName;
        this.userSex = userSex;
        this.nickName = nickName;
        this.birthday = birthday;
    }
}
