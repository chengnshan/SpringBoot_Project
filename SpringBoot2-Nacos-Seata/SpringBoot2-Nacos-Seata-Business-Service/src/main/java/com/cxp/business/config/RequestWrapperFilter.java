package com.cxp.business.config;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author : cheng
 * @date : 2020-08-28 23:36
 */
@Component
//@WebFilter(urlPatterns = "/**", description = "对所有请求进行包装,解决只能一次读取的问题")
public class RequestWrapperFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!(request instanceof CustomRequestWrapper)){
            request = new CustomRequestWrapper(request);
        }
        filterChain.doFilter(request, response);
    }
}
