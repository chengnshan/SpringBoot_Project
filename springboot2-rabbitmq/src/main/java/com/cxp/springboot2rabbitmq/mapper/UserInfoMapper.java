package com.cxp.springboot2rabbitmq.mapper;

import com.cxp.springboot2rabbitmq.rabbitConsumer.pojo.UserInfo;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author 程
 * @date 2019/7/3 上午10:59
 */
@Mapper
public interface UserInfoMapper {

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
            @Result(property = "jobs",column = "jobs")
    })
    public List<UserInfo> findUserInfoList(UserInfo userInfo);

    @Select(value = "select id,user_name,pass_word,user_sex,nick_name,birthday,jobs from user_info" +
            " where id = #{id}")
    @ResultMap(value = "userInfoResultMap")
    public UserInfo getUserInfoById(Integer id);
}
