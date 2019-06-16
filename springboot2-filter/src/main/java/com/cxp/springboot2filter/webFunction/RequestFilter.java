package com.cxp.springboot2filter.webFunction;

import com.cxp.springboot2filter.cover.RequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author 程
 * @date 2019/6/14 上午11:28
 */

@WebFilter(urlPatterns = {"/*"},filterName = "requestFilter")
@Order(value = 0)
public class RequestFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(RequestFilter.class);

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if(request instanceof HttpServletRequest) {
            requestWrapper = new RequestWrapper((HttpServletRequest) request);
        }
        if(requestWrapper == null) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("初始化RequestFilter过滤器");
    }

    @Override
    public void destroy() {

    }
}
