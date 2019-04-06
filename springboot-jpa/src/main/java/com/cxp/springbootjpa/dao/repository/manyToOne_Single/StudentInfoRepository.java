package com.cxp.springbootjpa.dao.repository.manyToOne_Single;

import com.cxp.springbootjpa.entity.manyToOne_single.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/30 下午6:53
 */
public interface StudentInfoRepository extends JpaRepository<StudentInfo,Integer> {
}
