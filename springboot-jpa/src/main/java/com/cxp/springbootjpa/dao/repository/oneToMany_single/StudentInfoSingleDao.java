package com.cxp.springbootjpa.dao.repository.oneToMany_single;

import com.cxp.springbootjpa.entity.oneToMany_single.StudentInfoSingle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/30 下午6:53
 */
public interface StudentInfoSingleDao extends JpaRepository<StudentInfoSingle,Integer> {
}
