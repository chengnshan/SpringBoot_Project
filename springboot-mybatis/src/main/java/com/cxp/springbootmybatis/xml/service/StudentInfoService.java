package com.cxp.springbootmybatis.xml.service;

import com.cxp.springbootmybatis.xml.pojo.StudentInfo;

import java.util.List;

/**
 * @author 程
 * @date 2019/4/14 下午1:15
 */
public interface StudentInfoService {

    public List<StudentInfo> findStudentByObj(StudentInfo studentInfo);
}
