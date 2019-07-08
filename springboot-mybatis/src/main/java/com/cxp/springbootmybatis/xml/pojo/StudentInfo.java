package com.cxp.springbootmybatis.xml.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 程
 * @date 2019/4/14 下午1:00
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class StudentInfo implements Serializable {

    private Integer id;
    private String name;
    private Integer age;

    @JsonFormat(pattern="YYYY-MM-dd")
    private Date birthday;
    private String address;
    private String classesId;

    private ClassesInfo classesInfo;
}
