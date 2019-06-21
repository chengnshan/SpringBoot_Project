package com.cxp.springboot2filter.config;

import com.cxp.springboot2filter.webFunction.MyInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 程
 * @date 2019/6/13 下午10:02
 */

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //注册某个拦截器的时候，同时排除某些不拦截的路径
        registry.addInterceptor(new MyInterceptor()).addPathPatterns("/**").excludePathPatterns("/cors/**","/static/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }

    /**
     * 添加静态资源文件，外部可以直接访问地址 或 在配置文件中配置
     * @param registry
     */
    /*@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //第一个方法设置访问路径前缀，第二个方法设置资源路径
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        WebMvcConfigurer.super.addResourceHandlers(registry);
    }*/

}
