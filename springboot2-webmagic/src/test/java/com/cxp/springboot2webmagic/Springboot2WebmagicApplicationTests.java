package com.cxp.springboot2webmagic;

import com.cxp.springboot2webmagic.service.JobInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class Springboot2WebmagicApplicationTests {

    @Autowired
    private JobInfoService jobInfoService;

    @Test
    public void contextLoads() {
        System.out.println(jobInfoService.getById(1));
    }

}
