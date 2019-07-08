package com.cxp.springbootmybatis.xml.mapper;

import com.cxp.springbootmybatis.xml.pojo.ClassesInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 程
 * @date 2019/7/8 下午8:08
 */
@Mapper
public interface ClassesInfoMapper {

    public List<ClassesInfo> findClassesInfoList(ClassesInfo classesInfo);

    public ClassesInfo getClassesInfoByClassesId(String classesId);
}
