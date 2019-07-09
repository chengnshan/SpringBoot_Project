package com.cxp.springbootmybatis.xml.mapper;

import com.cxp.springbootmybatis.xml.pojo.StudentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author 程
 * @date 2019/4/14 下午1:02
 */
@Mapper
public interface StudentInfoMapper {

    public List<StudentInfo> findStudentByObj(StudentInfo studentInfo);

    public StudentInfo getStudentById(Integer id);

    public List<StudentInfo> findStudentInfoLsitByClassesId(String classesId);

    /**
     * 批量添加
     * @param stuNames
     * @return
     */
    public int insertBatch(List<StudentInfo> stuNames);

    /**
     * 批量删除
     * @param stuNames
     * @return
     */
    public int deleteBatch(@Param(value = "stuNames") List<StudentInfo> stuNames);
}
