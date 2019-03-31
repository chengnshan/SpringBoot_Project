package com.cxp.springbootjpa.dao.manyToOne_Single;

import com.cxp.springbootjpa.entity.manyToOne_single.ClassRoom;
import com.cxp.springbootjpa.entity.manyToOne_single.StudentInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/3/30 下午6:56
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentInfoDaoTest {

    @Autowired
    private StudentInfoDao studentInfoDao;

    @Autowired
    private ClassRoomDao classRoomDao;

    @Test
    public void test1() {
        ClassRoom classRoom = new ClassRoom();
        classRoom.setCid("001002");
        classRoom.setCname("一年级二班");

        StudentInfo studentInfo = new StudentInfo();
        studentInfo.setStudentNo("10001");
        studentInfo.setBirthday(new Date());
        studentInfo.setGender("男");
        studentInfo.setClassRoom(classRoom);

        classRoomDao.save(classRoom);
        studentInfoDao.save(studentInfo);
    }

    @Test
    public void test2() {
        studentInfoDao.deleteById(2);
    }
}