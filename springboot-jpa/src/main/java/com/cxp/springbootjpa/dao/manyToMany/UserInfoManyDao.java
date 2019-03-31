package com.cxp.springbootjpa.dao.manyToMany;

import com.cxp.springbootjpa.entity.manyToMany.UserInfoMany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/31 下午4:42
 */
public interface UserInfoManyDao extends JpaRepository<UserInfoMany,Long> {
}
