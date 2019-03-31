package com.cxp.springbootjpa.entity.oneToMany_single;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * @author 程
 * @date 2019/3/25 下午6:05
 *
 * 一对多单向外键关联
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CLASS_ROOM_SINGLE")
public class ClassRoomSingle implements Serializable {

    private static final long serialVersionUID = -2232577608265962953L;
    @Id
    @GeneratedValue(generator = "cid")
    @GenericGenerator(name="cid",strategy = "assigned")
    @Column(name = "CID",length = 10)
    private String cid;

    @Column(name = "CNAME")
    private String cname;

    @OneToMany(cascade = {CascadeType.ALL},fetch = FetchType.LAZY)
    @JoinColumn(name = "cid")
    private Set<StudentInfoSingle> studentInfoSingles = new HashSet<>();
}
