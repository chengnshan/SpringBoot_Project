package com.cxp.h2database;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;
import java.util.Map;

@SpringBootTest //提供spring依赖注入
//@ExtendWith(SpringExtension.class)//导入spring测试框架[2]
public class SpringbootH2DatabaseApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // 注入web环境的ApplicationContext容器
    @Autowired
    private WebApplicationContext context;

    //MockMvc 测试http请求
    private MockMvc mockMvc;

    @BeforeAll
    static void setUp() {
        System.out.println("BeforeAll setUp.");
    }

    @BeforeEach
    void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }
    @Test
//    @DisplayName(value = "h2")
    public void contextLoads() throws Exception {
        List<Map<String, Object>> mapList = jdbcTemplate.queryForList("select  * from  t_user;");
        System.out.println(mapList);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/test/custRuntimeException")
                .param("username", "Jack")
                .param("password", "Jack001"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print()).andReturn();
        System.out.println("输出 " + mvcResult.getResponse().getContentAsString());
    }
}
