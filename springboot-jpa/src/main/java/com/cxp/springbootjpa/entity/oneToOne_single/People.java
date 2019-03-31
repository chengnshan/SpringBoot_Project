package com.cxp.springbootjpa.entity.oneToOne_single;

import com.cxp.springbootjpa.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 程
 * @date 2019/3/24 上午10:09
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PEOPLE")
public class People extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "PEOPLE_NAME",columnDefinition = "varchar(50) comment '用户名字' ")
    private String peopleName;
    @Column(name = "ADDRESS",columnDefinition = "varchar(256) comment '用户住址' ")
    private String address;
    @Column(name = "BIRTHDAY",columnDefinition = "datetime comment '用户住址' ")
    @Temporal(TemporalType.TIMESTAMP)
    private Date birthday;
    /** CascadeType.ALL : People是关系的维护端，当删除 people，会级联删除 icard*/
    @OneToOne(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinColumn(name = "ID",referencedColumnName = "ID",unique = true)
    private Icard icard;
}
