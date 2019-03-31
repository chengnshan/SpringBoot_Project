package com.cxp.springbootjpa.entity.oneToOne_single;

import com.cxp.springbootjpa.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 程
 * @date 2019/3/24 上午10:13
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "ICARD",uniqueConstraints = {@UniqueConstraint(columnNames = {"CARD_NO"})})
public class Icard extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;

    @Column(name = "CARD_NO",columnDefinition = "varchar(18) comment '用户身份证号' ")
    private String cardNo;
}
