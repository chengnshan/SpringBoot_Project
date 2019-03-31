package com.cxp.springbootjpa.entity.oneToOne_three;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

/**
 * @author 程
 * @date 2019/3/31 下午12:31
 *
 * 通过关联表的方式来保存一对一的关系
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table
public class AddressThree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;//id

    @Column(name = "phone", nullable = true, length = 11)
    private String phone;//手机

    @Column(name = "zipcode", nullable = true, length = 6)
    private String zipcode;//邮政编码

    @Column(name = "address", nullable = true, length = 100)
    private String address;//地址
}
