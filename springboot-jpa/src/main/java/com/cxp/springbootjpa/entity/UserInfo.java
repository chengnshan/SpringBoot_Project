package com.cxp.springbootjpa.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 程
 * @date 2019/3/23 上午11:17
 */
@Entity
@Table(name = "JPA_USER_INFO",uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_NO"})})
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "USER_NO",columnDefinition = "varchar(50) comment '用户账号' ")
    private String userNo;
    @Column(name = "USER_NAME",columnDefinition = "varchar(50) comment '用户名字' ")
    private String userName;
    @Column(name = "PHONE_NUMBER",columnDefinition = "varchar(11) comment '用户手机号' ")
    private String phoneNumber;
    @Column(name = "LAST_LOGIN_TIME",columnDefinition = "datetime comment '用户最后登录时间' ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastLoginTime;
}
