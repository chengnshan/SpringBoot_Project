package com.cxp.springboot2jspshiro.controller;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-09-19 22:18
 */
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class JspController {

    @RequestMapping("/jsp")
    public String toJps(Model model) {
        model.addAttribute("welcome", "不建议使用jsp");
        System.out.println("toJps");
        return "welcome";
    }

    @RequestMapping("/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("/login");
        System.out.println("login");
        return modelAndView;
    }
}
