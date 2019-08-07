package com.cxp.springbootmybatis.xml.pojo;

import lombok.*;

import java.io.Serializable;
import java.util.List;

/**
 * @author 程
 * @date 2019/7/8 下午8:05
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClassesInfo implements Serializable {

    private Integer id;
    private String classesId;
    private String classesName;

    private List<StudentInfo> stus;

    @Override
    public String toString() {
        return "ClassesInfo{" +
                "id=" + id +
                ", classesId='" + classesId + '\'' +
                ", classesName='" + classesName + '\'' +
                '}';
    }
}
