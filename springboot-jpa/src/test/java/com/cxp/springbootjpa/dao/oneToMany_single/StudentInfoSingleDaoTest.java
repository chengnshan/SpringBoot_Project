package com.cxp.springbootjpa.dao.oneToMany_single;

import com.cxp.springbootjpa.entity.oneToMany_single.ClassRoomSingle;
import com.cxp.springbootjpa.entity.oneToMany_single.StudentInfoSingle;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/3/30 下午10:28
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentInfoSingleDaoTest {

    @Autowired
    private StudentInfoSingleDao studentInfoSingleDao;

    @Autowired
    private ClassRoomSingleDao classRoomSingleDao;

    @Test
    public void test1(){
        StudentInfoSingle studentInfo1 = new StudentInfoSingle();
        studentInfo1.setStudentNo("10002");
        studentInfo1.setBirthday(new Date());
        studentInfo1.setGender("男");

        StudentInfoSingle studentInfo2 = new StudentInfoSingle();
        studentInfo2.setStudentNo("10003");
        studentInfo2.setBirthday(new Date());
        studentInfo2.setGender("女");

        ClassRoomSingle classRoomSingle = new ClassRoomSingle();
        classRoomSingle.setCid("001002");
        classRoomSingle.setCname("一年级二班");

        studentInfoSingleDao.save(studentInfo1);
        studentInfoSingleDao.save(studentInfo2);

        classRoomSingle.getStudentInfoSingles().add(studentInfo1);
        classRoomSingle.getStudentInfoSingles().add(studentInfo2);
        classRoomSingleDao.save(classRoomSingle);
    }
}