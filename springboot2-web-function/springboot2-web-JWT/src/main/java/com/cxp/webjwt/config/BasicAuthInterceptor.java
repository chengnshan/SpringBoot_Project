package com.cxp.webjwt.config;

import com.alibaba.fastjson.JSON;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author : cheng
 * @date : 2020-10-31 21:49
 */
public class BasicAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        String mappingUrl = requestURI.substring(requestURI.lastIndexOf('/'), requestURI.length());
        if ("/basicAuthentication".equals(mappingUrl)){
            String authorization = request.getHeader("Authorization");
            String result = "Basic " + Base64.getEncoder().encodeToString(("username:123456").getBytes());
            if (result.equals(authorization)){
                return true;
            }
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setHeader("Cache-Control", "no-store");
            response.setDateHeader("Expires", 0);
            PrintWriter writer = response.getWriter();
            writer.write("401 Authorization");
            writer.flush();
            writer.close();
            return false;
        }
        return true;
    }

}
