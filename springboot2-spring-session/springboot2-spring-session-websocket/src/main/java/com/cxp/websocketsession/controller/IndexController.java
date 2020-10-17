package com.cxp.redissession.controller;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * @author : cheng
 * @date : 2020-03-29 21:04
 */
@Controller
public class IndexController {

    @RequestMapping(value = "/login")
    public String login(HttpServletRequest request, HttpServletResponse response, String username, String password) {
        if ("username".equals(username) && "123456".equals(password)){
            request.getSession().setAttribute("username", username);
            Cookie cookie = new Cookie("cookieName","123456");
            cookie.setPath("/");
            response.addCookie(cookie);
            return "redirect:/";
        }
        return "login";
    }

    @RequestMapping(value = {"/","/index"})
    public String toIndexPage(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if ( !StringUtils.isEmpty(username) ){
            return "index";
        }
        return "login";
    }
}
