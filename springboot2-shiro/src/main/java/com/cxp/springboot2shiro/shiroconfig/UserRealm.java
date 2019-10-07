package com.cxp.springboot2shiro.shiroconfig;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxp.springboot2shiro.pojo.UserInfo;
import com.cxp.springboot2shiro.service.UserInfoService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-05 09:40
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 角色权限和对应权限添加
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取登录用户名
        String currentLoginName = (String) principals.getPrimaryPrincipal();
        //从数据库查询用户信息

        //为当前角色设置角色和权限
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
    //    authorizationInfo.addRoles();
    //    authorizationInfo.addStringPermissions();
        return authorizationInfo;
    }

    /**
     * 用户认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
        if (token.getPrincipal() == null) {
            return null;
        }
        //获取用户信息
        String name = token.getPrincipal().toString();

        QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",name);
        List<UserInfo> userInfos = userInfoService.list(wrapper);
        UserInfo userInfo = userInfos != null ? userInfos.get(0) : null;
        if (userInfo == null) {
            //这里返回后会报出对应异常
            return null;
        } else {
            //这里验证authenticationToken和simpleAuthenticationInfo的信息
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
                    name, userInfo.getPassword(), ByteSource.Util.bytes(userInfo.getSalt()), getName());
            return simpleAuthenticationInfo;
        }
    }
}
