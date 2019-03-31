package com.cxp.springbootjpa.entity.oneToOne_three;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author 程
 * @date 2019/3/31 下午12:31
 *
 * 通过关联表的方式来保存一对一的关系。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class PersonThree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = true, length = 20)
    private String name;//姓名

    @Column(name = "sex", nullable = true, length = 1)
    private String sex;//性别

    @Column(name = "birthday", nullable = true)
    private Timestamp birthday;//出生日期

    @OneToOne(cascade=CascadeType.ALL)//Person是关系的维护端
    @JoinTable(name = "person_address_three",
            joinColumns = @JoinColumn(name="person_id"),
            inverseJoinColumns = @JoinColumn(name = "address_id"))//通过关联表保存一对一的关系
    private AddressThree addressThree;//地址
}
