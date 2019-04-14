package com.cxp.springbootmybatis.config;

import com.github.pagehelper.PageHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * @author 程
 * @date 2019/4/14 下午1:32
 */
//@Configuration
public class MybatisConfig {

 //   @Bean
    public PageHelper pageHelper(){
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");

        p.setProperty("reasonable", "true");

        p.setProperty("helperDialect", "mysql");
        p.setProperty("pageSizeZero", "true");
        p.setProperty("params", "count=countSql");
        p.setProperty("supportMethodsArguments", "true");
        pageHelper.setProperties(p);
        return pageHelper;
    }
}
