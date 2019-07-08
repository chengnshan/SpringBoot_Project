package com.cxp.springbootmybatis.annotation.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author 程
 * @date 2019/7/8 上午9:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@NameStyle(value = Style.camelhumpAndLowercase)
public class RoleInfo implements Serializable {

    private static final long serialVersionUID = 7874518632081572019L;

    private String id;

    private String roleId;

    private String roleName;
}
