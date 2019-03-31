package com.cxp.springbootjpa.dao.oneToMany_single;

import com.cxp.springbootjpa.entity.oneToMany_single.ClassRoomSingle;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/30 下午6:52
 */
public interface ClassRoomSingleDao extends JpaRepository<ClassRoomSingle,String> {
}
