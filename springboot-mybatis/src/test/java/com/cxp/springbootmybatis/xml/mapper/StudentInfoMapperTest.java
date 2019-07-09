package com.cxp.springbootmybatis.xml.mapper;

import com.cxp.springbootmybatis.xml.pojo.StudentInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/7/8 下午9:27
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentInfoMapperTest {

    @Autowired
    private StudentInfoMapper studentInfoMapper;

    @Test
    public void findStudentInfoLsitByClassesId() {
        List<StudentInfo> class1001 = studentInfoMapper.findStudentInfoLsitByClassesId("class1001");
        System.out.println(class1001);
    }

    @Test
    public void insertBatch(){
        List<StudentInfo> infoList = studentInfoMapper.findStudentByObj(null);
        System.out.println(infoList);

        System.out.println(studentInfoMapper.deleteBatch(infoList));

        infoList = new ArrayList<>();
        infoList.add(new StudentInfo(null,"张三丰",122,new Date(),"湖北武当山",
                "class1001",null));
        infoList.add(new StudentInfo(null,"洪七公",89,new Date(),"湖北襄阳",
                "class1001",null));

        System.out.println(studentInfoMapper.insertBatch(infoList));
    }
}