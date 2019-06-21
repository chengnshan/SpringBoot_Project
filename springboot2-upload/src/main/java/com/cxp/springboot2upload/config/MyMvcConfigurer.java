package com.cxp.springboot2upload.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 程
 * @date 2019/6/16 下午5:57
 */
@Configuration
public class MyMvcConfigurer implements WebMvcConfigurer {

    /**
     * 映射访问路径到本地目录
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:/Volumes/Passport_2/study_practice_file/images/");
    }
}
