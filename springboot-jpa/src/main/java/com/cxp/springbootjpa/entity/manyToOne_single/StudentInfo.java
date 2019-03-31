package com.cxp.springbootjpa.entity.manyToOne_single;

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
 */

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "STUDENT_INFO")
public class StudentInfo implements Serializable {

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

    /**
     * many-to-one的常用属性：
     * name：映射类属性的名称（必须）
     * class：关联类的完全限定名
     * column：关联的字段
     * not-null：设置关联的字段的值是否可以为空。默认值false
     * lazy：指定关联对象是否使用延迟加载以及延迟加载的策略。lazy属性有三个取值：false、proxy、no-proxy。默认值proxy
     * fetch：设置抓取数据的策略。默认值是select
     * many-to-one没有inverse属性，因为关系的维护是多的一方，不可能放弃对关系的维护。
     */
    @ManyToOne(cascade = {CascadeType.REFRESH,CascadeType.REMOVE},fetch = FetchType.EAGER)
    @JoinColumn(name = "cid",referencedColumnName = "CID")
    private ClassRoom classRoom;
}
