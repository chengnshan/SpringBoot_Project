package com.cxp.springboot2cros.config;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * 自定义跨域过滤器继承CorsFilter，从而实现跨域请求
 * @author 程
 * @date 2019/6/15 下午9:45
 */
@Component
@Order(value = 3)
public class CustomCorsFilter extends CorsFilter {

    public CustomCorsFilter(){
        super(configurationSource());
    }

    private static UrlBasedCorsConfigurationSource configurationSource() {
        //1. 添加 CORS配置信息
        CorsConfiguration config = new CorsConfiguration();
        //是否发送 Cookie
        config.setAllowCredentials(true);
        //放行哪些原始域
        config.addAllowedOrigin("*");
        //放行哪些原始请求头部信息
        config.addAllowedHeader("*");
        //暴露哪些头部信息
        config.addExposedHeader("*");
        config.setMaxAge(36000L);
        //放行哪些请求方式
        config.setAllowedMethods(Arrays.asList("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS"));
        //2. 添加映射路径
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/corsFilter/**", config);
        return source;
    }

}
