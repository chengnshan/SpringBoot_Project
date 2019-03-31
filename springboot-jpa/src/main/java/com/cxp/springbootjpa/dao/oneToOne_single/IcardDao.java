package com.cxp.springbootjpa.dao.oneToOne_single;

import com.cxp.springbootjpa.entity.oneToOne_single.Icard;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/24 下午12:02
 */
public interface IcardDao extends JpaRepository<Icard,String> {
}
