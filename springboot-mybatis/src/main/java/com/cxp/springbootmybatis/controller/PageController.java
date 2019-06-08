package com.cxp.springbootmybatis.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author 程
 * @date 2019/6/7 下午7:38
 */
@Controller
public class PageController {

    @RequestMapping(value = "/toIamagePage.html")
    public String toIamagePage(){
        return "/html/imageInit";
    }
}
