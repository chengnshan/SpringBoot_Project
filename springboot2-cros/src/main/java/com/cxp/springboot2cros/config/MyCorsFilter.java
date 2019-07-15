package com.cxp.springboot2cros.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 程
 * @date 2019/6/9 下午9:27
 */
@WebFilter(filterName = "corsFilter",urlPatterns ={"/filter/*"})
@Order(value = 1)
public class MyCorsFilter implements Filter {

    /** 日志对象*/
    private static Logger logger = LoggerFactory.getLogger(MyCorsFilter.class);

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) {
        logger.info("初始化CorsFilter过滤器");
    }

    @Override
    public void destroy() {
        // destroy something
    }
}
