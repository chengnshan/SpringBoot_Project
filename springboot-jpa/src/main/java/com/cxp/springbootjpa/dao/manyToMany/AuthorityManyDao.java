package com.cxp.springbootjpa.dao.manyToMany;

import com.cxp.springbootjpa.entity.manyToMany.AuthorityMany;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author 程
 * @date 2019/3/31 下午4:42
 */
public interface AuthorityManyDao extends JpaRepository<AuthorityMany,String> {
}
