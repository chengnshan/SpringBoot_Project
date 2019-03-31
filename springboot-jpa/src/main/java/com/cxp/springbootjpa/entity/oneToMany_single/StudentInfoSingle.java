package com.cxp.springbootjpa.entity.oneToMany_single;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 程
 * @date 2019/3/25 下午6:01
 *
 * 一对多单向外键关联
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STUDENT_INFO_SINGLE")
public class StudentInfoSingle implements Serializable {

    private static final long serialVersionUID = -5148450134982514258L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "STUDENT_NO",columnDefinition = "varchar(50) comment '学号' ")
    private String studentNo;

    @Column(name = "GENDER",columnDefinition = "varchar(50) comment '姓别' ")
    private String gender;

    @Column(name = "BIRTHDAY",columnDefinition = "varchar(50) comment '生日' ")
    private Date birthday;

}
