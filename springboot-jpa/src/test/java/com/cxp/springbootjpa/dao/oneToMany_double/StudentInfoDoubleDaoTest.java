package com.cxp.springbootjpa.dao.oneToMany_double;

import com.cxp.springbootjpa.entity.oneToMany_double.ClassRoomDouble;
import com.cxp.springbootjpa.entity.oneToMany_double.StudentInfoDouble;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

/**
 * @author 程
 * @date 2019/3/31 上午10:43
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class StudentInfoDoubleDaoTest {

    @Autowired
    private StudentInfoDoubleDao studentInfoDoubleDao;

    @Autowired
    private ClassRoomDoubleDao classRoomDoubleDao;

    @Test
    public void test1(){
        StudentInfoDouble studentInfo1 = new StudentInfoDouble();
        studentInfo1.setStudentNo("10002");
        studentInfo1.setBirthday(new Date());
        studentInfo1.setGender("男");

        StudentInfoDouble studentInfo2 = new StudentInfoDouble();
        studentInfo2.setStudentNo("10003");
        studentInfo2.setBirthday(new Date());
        studentInfo2.setGender("女");

        ClassRoomDouble classRoomDouble = new ClassRoomDouble();
        classRoomDouble.setCid("001002");
        classRoomDouble.setCname("一年级二班");
        classRoomDouble.getStudentInfoDoubles().add(studentInfo1);
        classRoomDouble.getStudentInfoDoubles().add(studentInfo2);

        Assert.assertNotNull(classRoomDoubleDao.save(classRoomDouble));
    }

    @Test
    public void test2(){
        List<ClassRoomDouble> classRooms = classRoomDoubleDao.findAll();
        classRooms.forEach(classRoomDouble -> {
            classRoomDouble.getStudentInfoDoubles().forEach(studentInfoDouble -> {
                System.out.println(studentInfoDouble.getStudentNo());
            });
        });
    }
}