package com.cxp.springbootjpa.dao.dao.manyToMany;

import com.cxp.springbootjpa.entity.manyToMany.UserInfoMany;

import java.util.List;

/**
 * @author 程
 * @date 2019/4/6 下午5:43
 */
public interface UserInfoManyDao {

    public List<UserInfoMany> queryUserInfoManys(UserInfoMany userInfoMany);
}
