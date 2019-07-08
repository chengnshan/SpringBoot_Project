package com.cxp.springbootmybatis.annotation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author 程
 * @date 2019/7/8 上午9:36
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRoleInfo implements Serializable {

    private static final long serialVersionUID = -6693004984841445814L;

    private String id;
    private String userName;
    private String roleId;
}
