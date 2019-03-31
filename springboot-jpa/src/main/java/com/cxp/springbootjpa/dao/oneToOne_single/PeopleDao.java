package com.cxp.springbootjpa.dao.oneToOne_single;

import com.cxp.springbootjpa.entity.oneToOne_single.People;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/24 上午11:49
 */
public interface PeopleDao extends JpaRepository<People,Integer> {
}
