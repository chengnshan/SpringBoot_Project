package com.cxp.springbootmybatis.annotation.mapper;

import com.cxp.springbootmybatis.annotation.pojo.UserInfo;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.mapping.FetchType;

import java.util.List;

/**
 * @author 程
 * @date 2019/7/3 上午10:59
 */
@Mapper
public interface UserInfoMapper extends tk.mybatis.mapper.common.Mapper<UserInfo> {

    /**
     * 根据对象查询
     * @param userInfo
     * @return
     */
    @Select(value = "<script>" +
            " select id,user_name,pass_word,user_sex,nick_name,birthday,jobs from user_info " +
            " where 1=1" +
            " <if test= \"id != null \">" +
            "   and id = #{id}" +
            " </if>" +
            " <if test= \"userName != null and userName != '' \">" +
            "   and user_name = #{userName}" +
            " </if>" +
            " <if test=\"passWord != null and passWord != '' \" > " +
            "   and pass_word = #{passWord}" +
            " </if>"+
            " <if test=\"nickName != null and nickName != '' \" > " +
            "   and nick_name = #{nickName}" +
            " </if>"+
            " </script>")
    @Results(id = "userInfoResultMap",value = {
            @Result(property = "id",column = "id",id = true),
            @Result(property = "userName",column = "user_name"),
            @Result(property = "passWord",column = "pass_word"),
            @Result(property = "userSex",column = "user_sex"),
            @Result(property = "nickName",column = "nick_name"),
            @Result(property = "birthday",column = "birthday"),
            @Result(property = "jobs",column = "jobs"),
            @Result(property = "roleInfos",column = "user_name",
                    many = @Many(select = "com.cxp.springbootmybatis.annotation.mapper.RoleInfoMapper.findRoleInfoByUserName",
                    //懒加载
                    fetchType = FetchType.LAZY))
    })
    public List<UserInfo> findUserInfoList(UserInfo userInfo);

    /**
     * 根据Id查询
     * @param id
     * @return
     */
    @Select(value = "select id,user_name,pass_word,user_sex,nick_name,birthday,jobs from user_info" +
            " where id = #{id}")
    @ResultMap(value = "userInfoResultMap")
    public UserInfo getUserInfoById(Integer id);


    /**
     *
     * @param userInfo
     * @return
     */
    @Insert(value = "insert into user_info (user_name,pass_word,user_sex,nick_name,birthday,jobs) " +
            " values (#{userName},#{passWord},#{userSex},#{nickName},#{birthday},#{jobs})")
    public int insertUserInfo(UserInfo userInfo);


    /**
     * 批量插入
     * @param userInfos
     * @return
     */
    @Insert(value = "<script>" +
            " insert into user_info (user_name,pass_word,user_sex,nick_name,birthday,jobs) " +
            " values " +
            " <foreach collection=\"list\" item=\"item\" separator=\",\" >" +
            " ( #{item.userName},#{item.passWord},#{item.userSex},#{item.nickName},#{item.birthday},#{item.jobs})" +
            " </foreach> " +
            "</script>")
    public int batchInsertUserInfo(List<UserInfo> userInfos);
}
