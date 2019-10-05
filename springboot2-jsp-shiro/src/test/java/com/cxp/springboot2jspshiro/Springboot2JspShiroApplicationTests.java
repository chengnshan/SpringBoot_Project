package com.cxp.springboot2jspshiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cxp.springboot2jspshiro.pojo.UserInfo;
import com.cxp.springboot2jspshiro.service.UserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Springboot2JspShiroApplicationTests {

	@Autowired
	private UserInfoService userInfoService;

	@Test
	public void contextLoads() {
		System.out.println(userInfoService.list());

		QueryWrapper<UserInfo> wrapper = new QueryWrapper<>();
		wrapper.eq("user_name","user1");
		List<UserInfo> userInfos = userInfoService.list(wrapper);
		System.out.println(userInfos);
	}

}
