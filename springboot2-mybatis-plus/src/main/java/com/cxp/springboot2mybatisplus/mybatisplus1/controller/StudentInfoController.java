package com.cxp.springboot2mybatisplus.mybatisplus1.controller;


import com.cxp.springboot2mybatisplus.mybatisplus1.pojo.StudentInfo;
import com.cxp.springboot2mybatisplus.mybatisplus1.service.IStudentInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author cheng
 * @since 2019-10-02
 */
@RestController
@RequestMapping("/mybatisplus1/studentInfo")
public class StudentInfoController {

    @Autowired
    private IStudentInfoService iStudentInfoService;

    @RequestMapping(value = "/getStudentList")
    public List<StudentInfo> getStudentList(){
        List<StudentInfo> list = iStudentInfoService.list(null);
        return list;
    }

}
