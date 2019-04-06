package com.cxp.springbootjpa.controller;

import com.cxp.springbootjpa.dao.repository.oneToMany_double.ClassRoomDoubleRepository;
import com.cxp.springbootjpa.dao.repository.oneToMany_double.StudentInfoDoubleRepository;
import com.cxp.springbootjpa.entity.oneToMany_double.ClassRoomDouble;
import com.cxp.springbootjpa.entity.oneToMany_double.StudentInfoDouble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * @author 程
 * @date 2019/3/31 上午11:39
 */
@RestController
public class StudentInfoDoubleController {

    @Autowired
    private StudentInfoDoubleRepository studentInfoDoubleDao;

    @Autowired
    private ClassRoomDoubleRepository classRoomDoubleDao;

    @RequestMapping(value = "/getStudentInfoDouble")
    public Set<StudentInfoDouble> getStudentInfoDouble(){
        List<ClassRoomDouble> classRooms = classRoomDoubleDao.findAll();
        return classRooms.get(0).getStudentInfoDoubles();
    }
}
