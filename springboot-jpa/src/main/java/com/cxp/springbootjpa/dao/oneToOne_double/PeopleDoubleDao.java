package com.cxp.springbootjpa.dao.oneToOne_double;

import com.cxp.springbootjpa.entity.oneToOne_double.People_Double;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 程
 * @date 2019/3/24 下午9:42
 */
public interface PeopleDoubleDao extends JpaRepository<People_Double,Integer>{

}
