package com.cxp.springboot2filter.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * @author 程
 * @date 2019/6/9 下午9:48
 */
@Controller
public class CorsController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 通过corsFilter过滤来实现跨域请求
     * @param params
     * @return
     */
    @RequestMapping(value = "/cors/corsOrignDataFilter")
    @ResponseBody
    public Map<String,Object> corsOrignDataFilter(@RequestParam Map<String,Object> params){
        logger.info(params.toString());
        params.put("code","success");
        return params;
    }

    /**
     * 通过注解 @CrossOrigin 来实现跨域请求
     * @param params
     * @return
     */
    @RequestMapping(value = "/anno/corsOrignDataAnnotation")
    @ResponseBody
    @CrossOrigin
    public Map<String,Object> corsOrignDataAnnotation(@RequestParam Map<String,Object> params){
        logger.info(params.toString());
        params.put("code","success");
        return params;
    }

    /**
     * 通过 继承使用Spring Web中的CorsFilter 来实现跨域请求
     * @param params
     * @return
     */
    @RequestMapping(value = "/extends/corsOrignDataExtends")
    @ResponseBody
    public Map<String,Object> corsOrignDataExtends(@RequestParam Map<String,Object> params){
        logger.info(params.toString());
        params.put("code","success");
        return params;
    }
}
