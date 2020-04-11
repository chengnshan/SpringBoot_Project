package com.cxp.redissessiontwo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.stream.Stream;


/**
 * @author : cheng
 * @date : 2020-03-29 21:04
 */
@Controller
@Slf4j
public class IndexController {

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, String username,String password) {
        if ("username".equals(username) && "123456".equals(password)){
            request.getSession().setAttribute("username", username);
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(value = {"/","/index"})
    public String toIndexPage(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        getCookie(request);
        if ( !StringUtils.isEmpty(username) ){
            return "index";
        }
        return "login";
    }

    private void getCookie(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        Stream.of(cookies).forEach(cookie -> {
            log.info("name: {}, value: {}",cookie.getName(),cookie.getValue());
        });
    }
}
