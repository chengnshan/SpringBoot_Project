package com.cxp.springboot2rsaaes.jsonview.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author 程
 * @date 2019/7/14 上午11:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp (){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    public void whenQuerySuccess () throws Exception{
        String result=mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .param("username","knyel")
                .param("age","18")
                .param("ageTo","60")
                .param("phone","110")
                .param("size","15")
                .param("page","2")
                .param("sort","age,desc")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value("3"))//查询的根元素，例如$.length()代表整个传过来的json的文档
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
     * 请求成功逻辑测试
     * @throws Exception
     */
    @Test
    public void whenGenInfoSuccess() throws Exception{
        String result=mockMvc.perform(MockMvcRequestBuilders.get("/user/1")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("tom"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("tom123"))
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    /**
     * 路径正则表达式的匹配规则测试
     * @throws Exception
     */
    @Test
    public void whenGetInfoFail() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/user/a")
                .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(MockMvcResultMatchers.status().is4xxClientError());
    }
}