package com.cxp.springbootjpa.entity.manyToOne_single;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author 程
 * @date 2019/3/25 下午6:05
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLASS_ROOM")
public class ClassRoom implements Serializable {

    private static final long serialVersionUID = -2232577608265962953L;
    @Id
    @GeneratedValue(generator = "cid")
    @GenericGenerator(name="cid",strategy = "assigned")
    @Column(name = "CID",length = 10)
    private String cid;

    @Column(name = "CNAME")
    private String cname;
}
