package com.cxp.springbootmybatis.xml.mapper;

import com.cxp.springbootmybatis.xml.pojo.ClassesInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/7/8 下午9:29
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ClassesInfoMapperTest {

    @Autowired
    private ClassesInfoMapper classesInfoMapper;

    @Test
    public void getClassesInfoByClassesId() {
        ClassesInfo class1001 = classesInfoMapper.getClassesInfoByClassesId("class1001");
        System.out.println(class1001.getStus());
    }
}