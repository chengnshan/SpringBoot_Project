package com.cxp.springboot2filter.webFunction;

import com.cxp.springboot2filter.cover.RequestWrapper;
import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @author 程
 * @date 2019/6/9 下午10:04
 */

@Component
@Log
public class MyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws Exception {
        log.info(">>>MyInterceptor>>>>>>>在请求处理之前进行调用（Controller方法调用之前）");
        String param = request.getParameter("param");
        String userName = request.getParameter("userName");

        log.info("param 参数值为: "+param);
        log.info("userName 参数值为: "+userName);


        RequestWrapper requestWrapper = new RequestWrapper(request);
        String body = requestWrapper.getBody();
        log.info("body 参数值为: "+body);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info(">>>MyInterceptor>>>>>>>请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）");
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                Exception ex) throws Exception {
        log.info(">>>MyInterceptor>>>>>>>在整个请求结束之后被调用，也就是在DispatcherServlet");
        // 渲染了对应的视图之后执行（主要是用于进行资源清理工作）");
    }
}
