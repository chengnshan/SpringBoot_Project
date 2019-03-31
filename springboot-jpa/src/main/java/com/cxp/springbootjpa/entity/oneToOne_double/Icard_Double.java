package com.cxp.springbootjpa.entity.oneToOne_double;

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
 * @date 2019/3/24 上午11:45
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ICARD_DOUBLE",uniqueConstraints = {@UniqueConstraint(columnNames = {"PID"})})
public class Icard_Double extends BaseEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "PID")
    /**assigned手工赋值*/
    @GenericGenerator(name = "PID",strategy = "assigned")
    @Column(length = 18)
    private String pid;
    @Column(name = "PNAME",columnDefinition = "varchar(50) comment '用户身份证号' ")
    private String pName;
    /**指定People_Double中的外键属性*/
    @OneToOne(mappedBy = "icard_double")
    private People_Double people_double;

    @Override
    public String toString() {
        return "Icard_Double{" +
                "pid='" + pid + '\'' +
                ", pName='" + pName + '\'' +
                '}';
    }
}
