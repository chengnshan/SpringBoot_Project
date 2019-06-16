package com.cxp.springboot2filter.controller;

import com.cxp.springboot2filter.pojo.UserInfo;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 程
 * @date 2019/6/14 上午9:13
 */
@Controller
@RequestMapping(value = "/req")
@Log
public class RequestController {

    @RequestMapping(value = "/getRequestBody")
    @ResponseBody
    public UserInfo getRequestBody(@RequestBody UserInfo userInfo, String param){
        log.info(userInfo.toString());

        return userInfo;
    }
}
