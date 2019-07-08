package com.cxp.springbootmybatis.principle.config;

import com.cxp.springbootmybatis.principle.pojo.DatabaseInfo;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/7/8 上午9:57
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrincipleConfigTest {

    @Autowired
    private PrincipleConfig principleConfig;

    @Test
    public void executorList() {
        String content = "<select id=\"findStudentByObj\" parameterType=\"map\" resultType=\"map\">\n" +
                " select id,name,age,birthday,address from student_info " +
                "<where>\n" +
                "            <if test=\"id != null\">\n" +
                "                id = #{id}\n" +
                "            </if>\n" +
                "            <if test=\" name != null and name != '' \">\n" +
                "                and name = #{name}\n" +
                "            </if>\n" +
                "            <if test=\" age != null \">\n" +
                "                and age = #{age}\n" +
                "            </if>\n" +
                "            <if test=\" address != null and address != '' \">\n" +
                "                and address = #{address}\n" +
                "            </if>\n" +
                "            <if test=\" birthday != null  \">\n" +
                "                and birthday = #{birthday, jdbcType=DATE}\n" +
                "            </if>\n" +
                "        </where>" +
                "    </select>";

        Map<String, Object> param = new HashMap<>();
        param.put("name","张三丰");
        param.put("age",122);
        param.put("address","湖北武当山");

        DatabaseInfo info =new DatabaseInfo();
        info.setHostName("192.168.153.128");
        info.setPort("3306");
        info.setDatabaseName("springboot2-project");

        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setPassword("123456");
        hikariConfig.setUsername("root");
        hikariConfig.setJdbcUrl(info.getUrl());
        hikariConfig.setDriverClassName("com.mysql.cj.jdbc.Driver");
        DataSource dataSource = new HikariDataSource(hikariConfig);


        List<Object> objects = principleConfig.executorList(param, content, dataSource);
        System.out.println(objects);

    }
}