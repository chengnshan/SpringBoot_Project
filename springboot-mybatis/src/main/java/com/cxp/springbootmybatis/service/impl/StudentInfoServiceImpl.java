package com.cxp.springbootmybatis.service.impl;

import com.cxp.springbootmybatis.mapper.StudentInfoMapper;
import com.cxp.springbootmybatis.pojo.StudentInfo;
import com.cxp.springbootmybatis.service.StudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 程
 * @date 2019/4/14 下午1:15
 */
@Service(value = "studentInfoService")
public class StudentInfoServiceImpl implements StudentInfoService {


    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Override
    public List<StudentInfo> findStudentByObj(StudentInfo studentInfo) {
        return studentInfoMapper.findStudentByObj(studentInfo);
    }
}
