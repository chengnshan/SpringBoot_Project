package com.cxp.springbootmybatis.service;

import com.cxp.springbootmybatis.pojo.StudentInfo;

import java.util.List;

/**
 * @author 程
 * @date 2019/4/14 下午1:15
 */
public interface StudentInfoService {

    public List<StudentInfo> findStudentByObj(StudentInfo studentInfo);
}
