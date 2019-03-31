package com.cxp.springbootjpa.dao.manyToOne_Single;

import com.cxp.springbootjpa.entity.manyToOne_single.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/30 下午6:52
 */
public interface ClassRoomDao extends JpaRepository<ClassRoom,String> {
}
