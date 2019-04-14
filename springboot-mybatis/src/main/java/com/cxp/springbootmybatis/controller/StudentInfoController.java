package com.cxp.springbootmybatis.controller;

import com.cxp.springbootmybatis.pojo.StudentInfo;
import com.cxp.springbootmybatis.service.StudentInfoService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author 程
 * @date 2019/4/14 下午1:24
 */
@RestController
public class StudentInfoController {

    @Autowired
    private StudentInfoService studentInfoService;

    @RequestMapping(value = "/queryStudentInfoList")
    public List<StudentInfo> queryStudentInfoList(
            StudentInfo studentInfo,
            @RequestParam(defaultValue = "1",required = false) int pageNum,
            @RequestParam(defaultValue = "10",required = false) int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        List<StudentInfo> studentInfos = studentInfoService.findStudentByObj(studentInfo);
        return studentInfos;
    }
}
