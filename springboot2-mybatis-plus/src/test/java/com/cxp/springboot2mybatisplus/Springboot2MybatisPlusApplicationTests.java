package com.cxp.springboot2mybatisplus;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cxp.springboot2mybatisplus.mybatisplus1.mapper.UserMapper;
import com.cxp.springboot2mybatisplus.mybatisplus1.pojo.StudentInfo;
import com.cxp.springboot2mybatisplus.mybatisplus1.pojo.User;
import com.cxp.springboot2mybatisplus.mybatisplus1.service.IStudentInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot2MybatisPlusApplicationTests {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private IStudentInfoService iStudentInfoService;

    @Test
    public void contextLoads() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
    //    Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);

        Page<User> page = new Page<>(3,5);
        IPage<User> userIPage = userMapper.selectPage(page, null);
        userIPage.getRecords().forEach(System.out::println);
    }

    @Test
    public void contextLoads11() {
        /*System.out.println(iStudentInfoService.save(new StudentInfo(null,"李四",15, LocalDate.now(),"湖北武汉")));
        System.out.println(iStudentInfoService.list());

        Map<String,Object> columnmap = new HashMap();
        columnmap.put("name","张三丰");
        System.out.println(iStudentInfoService.listByMap(columnmap));*/
    }
}
