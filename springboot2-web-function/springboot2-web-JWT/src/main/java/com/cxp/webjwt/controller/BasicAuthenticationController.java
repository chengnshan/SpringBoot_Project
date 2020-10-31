package com.cxp.webjwt.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author : cheng
 * @date : 2020-10-31 21:48
 */
@RestController
public class BasicAuthenticationController {

    @RequestMapping(value = "/basicAuthentication")
    public String basicAuthentication(@RequestBody String input){

        return input;
    }

}
