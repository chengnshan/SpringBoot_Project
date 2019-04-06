package com.cxp.springbootjpa.dao.impl.manyToMany;


import com.cxp.springbootjpa.dao.dao.manyToMany.UserInfoManyDao;
import com.cxp.springbootjpa.entity.manyToMany.UserInfoMany;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * @author 程
 * @date 2019/4/6 下午5:30
 */
@Repository
public class UserInfoManyRepositoryImpl implements UserInfoManyDao {

    @PersistenceContext
    private EntityManager entityManger;

    @Override
    public List<UserInfoMany> queryUserInfoManys(UserInfoMany userInfoMany) {
        String sql = "select * from userinfo_many";
        NativeQuery query = (NativeQuery)entityManger.createNativeQuery(sql);
        query.addEntity(UserInfoMany.class);
        List<UserInfoMany> resultList = query.getResultList();
        return resultList;
    }
}
