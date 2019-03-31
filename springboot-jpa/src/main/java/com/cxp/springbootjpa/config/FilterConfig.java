package com.cxp.springbootjpa.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate5.support.OpenSessionInViewFilter;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;

import java.util.Arrays;

/**
 * @author 程
 * @date 2019/3/31 上午11:30
 */

public class FilterConfig {

    @Bean
    public FilterRegistrationBean registrationBean(){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new OpenSessionInViewFilter());
        registrationBean.setName("openSessionInViewFilter");

        registrationBean.setUrlPatterns(Arrays.asList("/*"));
        registrationBean.setOrder(Integer.MAX_VALUE);

        return registrationBean;
    }
}
