package com.cxp.h2database.controller;

import com.cxp.h2database.config.CustRuntimeException;
import com.cxp.h2database.pojo.ResultCode;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author : cheng
 * @date : 2020-03-14 19:28
 */
@Controller
@RequestMapping(value = "/test")
public class ExceptionController {

    @RequestMapping(value = "custRuntimeException")
    @ResponseBody
    public String testException(String username,String password){
        System.out.println(ResultCode.valueOf("USERNAME_ERROR"));
        throw new CustRuntimeException(ResultCode.USERNAME_ERROR);
//        int i = 1/0;
//        return "testException";
    }
}
