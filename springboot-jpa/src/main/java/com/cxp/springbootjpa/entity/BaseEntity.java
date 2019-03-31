package com.cxp.springbootjpa.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 程
 * @date 2019/3/23 上午11:14
 */

@MappedSuperclass
public class BaseEntity implements Serializable {

    @Column(name = "CREATE_USER",columnDefinition = "varchar(30) comment '创建人'")
    private String createUser;

    @Column(name = "CREATE_TIME",columnDefinition = "datetime comment '创建时间'")
    private Date createTime;

    @Column(name = "UPDATE_USER",columnDefinition = "varchar(30) comment '更新人'")
    private String updateUser;

    @Column(name = "UPDATE_TIME",columnDefinition = "datetime comment '更新时间'")
    private Date updateTime;
}
