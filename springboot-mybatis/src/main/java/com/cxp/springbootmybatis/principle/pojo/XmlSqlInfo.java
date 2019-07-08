package com.cxp.springbootmybatis.principle.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 程
 * @date 2019/7/8 上午10:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class XmlSqlInfo {

    private Integer id;
    private String xmlSqlId;
    private String xmlSql;

}
