package com.cxp.springbootjpa.entity.oneToOne_double;

import com.cxp.springbootjpa.entity.BaseEntity;
import com.cxp.springbootjpa.entity.oneToOne_single.Icard;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author 程
 * @date 2019/3/24 上午11:44
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "PEOPLE_DOUBLE")
public class People_Double extends BaseEntity implements Serializable {
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

    @OneToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "PID",unique = true)
    private Icard_Double icard_double;
}
