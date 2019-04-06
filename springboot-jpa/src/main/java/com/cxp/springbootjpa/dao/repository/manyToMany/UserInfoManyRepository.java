package com.cxp.springbootjpa.dao.repository.manyToMany;

import com.cxp.springbootjpa.dao.dao.manyToMany.UserInfoManyDao;
import com.cxp.springbootjpa.entity.manyToMany.UserInfoMany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/31 下午4:42
 */
public interface UserInfoManyRepository extends JpaRepository<UserInfoMany,Long>,UserInfoManyDao {
}
