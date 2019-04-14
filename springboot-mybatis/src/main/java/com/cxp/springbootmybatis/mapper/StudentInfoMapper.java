package com.cxp.springbootmybatis.mapper;

import com.cxp.springbootmybatis.pojo.StudentInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 程
 * @date 2019/4/14 下午1:02
 */
@Mapper
public interface StudentInfoMapper {

    public List<StudentInfo> findStudentByObj(StudentInfo studentInfo);
}
