package com.cxp.springbootjpa.entity.manyToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 程
 * @date 2019/3/31 下午4:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "authority_many")
public class AuthorityMany {

    @Id
    @GeneratedValue(generator = "authority_id")
    @GenericGenerator(name = "authority_id",strategy = "assigned")
    @Column(name = "authority_id")
    private String authorityId;

    //权限名
    @Column(nullable = false)
    private String name;

}
