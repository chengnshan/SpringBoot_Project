package com.cxp.springbootjpa.dao.oneToOne_three;

import com.cxp.springbootjpa.entity.oneToOne_three.PersonThree;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/31 下午12:36
 */
public interface PersonThreeDao extends JpaRepository<PersonThree,Long> {
}
