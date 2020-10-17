package com.cxp.springboot2mybatisplus.mybatisplus1.service.impl;

import com.cxp.springboot2mybatisplus.mybatisplus1.pojo.StudentInfo;
import com.cxp.springboot2mybatisplus.mybatisplus1.mapper.StudentInfoMapper;
import com.cxp.springboot2mybatisplus.mybatisplus1.service.IStudentInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cheng
 * @since 2019-10-02
 */
@Service
public class StudentInfoServiceImpl extends ServiceImpl<StudentInfoMapper, StudentInfo> implements IStudentInfoService {

}
