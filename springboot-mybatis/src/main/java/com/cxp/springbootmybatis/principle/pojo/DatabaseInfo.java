package com.cxp.springbootmybatis.principle.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author 程
 * @date 2019/7/8 上午10:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DatabaseInfo {

    private Integer id;
    private String xmlSqlId;
    private String hostName; //主机名
    private String port; //商品
    private String databaseName;  //数据库名称
    private String url;
    private String driver;  //目标数据库驱动
    private String userName;
    private String password;

    public String getUrl(){
        return String.format(
                "jdbc:mysql://%s:%s/%s?useUnicode=true&characterEncoding=utf-8"
                ,this.hostName,this.port,this.getDatabaseName());
    }
}
