package com.cxp.springbootjpa.dao.oneToMany_double;

import com.cxp.springbootjpa.entity.oneToMany_double.StudentInfoDouble;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/30 下午6:53
 */
public interface StudentInfoDoubleDao extends JpaRepository<StudentInfoDouble,Integer> {
}
