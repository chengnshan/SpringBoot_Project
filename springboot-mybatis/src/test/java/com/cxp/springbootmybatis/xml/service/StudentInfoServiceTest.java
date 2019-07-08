package com.cxp.springbootmybatis.xml.service;

import com.cxp.springbootmybatis.xml.pojo.StudentInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/7/8 上午8:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentInfoServiceTest {

    @Autowired
    private StudentInfoService studentInfoService;

    @Test
    public void findStudentByObj() {

        List<StudentInfo> studentByObj = studentInfoService.findStudentByObj(null);
        System.out.println(studentByObj);
    }
}