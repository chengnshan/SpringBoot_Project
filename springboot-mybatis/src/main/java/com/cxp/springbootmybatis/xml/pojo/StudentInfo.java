package com.cxp.springbootmybatis.xml.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 程
 * @date 2019/4/14 下午1:00
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
public class StudentInfo implements Serializable {

    private Integer id;
    private String stuName;
    private Integer age;

    @JsonFormat(pattern="YYYY-MM-dd")
    private Date birthday;
    private String address;
    private String classesId;

    private ClassesInfo classesInfo;

}
