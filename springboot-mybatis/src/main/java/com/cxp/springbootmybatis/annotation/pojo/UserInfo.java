package com.cxp.springbootmybatis.annotation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

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

    @Id
    private Integer id;

    @Column(name = "user_name")
    private String userName;
    @Column(name = "pass_word")
    private String passWord;
    @Column(name = "user_sex")
    private String userSex;
    @Column(name = "nick_name")
    private String nickName;
    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "jobs")
    private String jobs;

    private List<RoleInfo> roleInfos;

    public UserInfo(Integer id, String userName, String passWord, String userSex, String nickName, Date birthday){
        this.id = id;
        this.userName = userName;
        this.passWord = passWord;
        this.userSex = userSex;
        this.nickName = nickName;
        this.birthday = birthday;
    }

}
