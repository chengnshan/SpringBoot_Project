package com.cxp.springbootmybatis.annotation.mapper;

import com.cxp.springbootmybatis.annotation.pojo.RoleInfo;
import org.apache.ibatis.annotations.*;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * 通用mapper https://github.com/abel533/Mapper/wiki/1.integration
 * 注解复杂映射 https://www.jianshu.com/p/7f2a160ca97d
 * @author 程
 * @date 2019/7/8 上午11:46
 */
@org.apache.ibatis.annotations.Mapper
public interface RoleInfoMapper extends Mapper<RoleInfo> {

    @Select(value = "<script>" +
            " select id,role_id,role_name from role_info where 1=1 " +
            " <if test= \"id != null \">" +
            "   and id = #{id}" +
            " </if>" +
            " <if test= \"roleId != null and roleId != '' \">" +
            "   and role_id = #{roleId}" +
            " </if>" +
            " <if test= \"roleName != null and roleName != '' \">" +
            "   and role_name = #{roleName}" +
            " </if>" +
            "</script>")
    @Results(id = "roleInfoResultMap",value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "roleId",column = "role_id"),
            @Result(property = "roleName",column = "role_name")
    })
    public List<RoleInfo> findRoleInfoList(RoleInfo roleInfo);


    /**
     * 根据用户名查询所有角色
     * @param userName
     * @return
     */
    @Select(value = "select bb.id,bb.role_id,bb.role_name " +
            " from user_role_info aa,role_info bb " +
            " where aa.role_id = bb.role_id" +
            " and aa.user_name = #{userName} ")
    @ResultMap(value = "roleInfoResultMap")
    public List<RoleInfo> findRoleInfoByUserName(@Param(value = "userName") String userName);
}
