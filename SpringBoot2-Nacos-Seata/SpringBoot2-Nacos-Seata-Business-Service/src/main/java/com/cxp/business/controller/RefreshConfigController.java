package com.cxp.business.controller;

import com.cxp.business.config.CustomRequestWrapper;
import com.cxp.business.pojo.UserInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author : cheng
 * @date : 2020-08-13 22:17
 */
@RestController
@Slf4j
public class RefreshConfigController {

//    @NacosValue(value = "${business.config.datasource.username}",autoRefreshed = true)
    private String datasourceUsername;

    @PostConstruct
    public void init(){
        System.out.println(datasourceUsername);
    }

    @RequestMapping(value = "/getConfigContent")
    public String getConfigContent(HttpServletRequest request){
        String body = null;
        if (request instanceof CustomRequestWrapper){
            body = ((CustomRequestWrapper)request).getBody();
        }
        System.out.println("getConfigContent body : == "+body);
        System.out.println("getConfigContent : == "+datasourceUsername);
        return body;
    }

    @PostMapping(value = "/getUserInfo")
    public UserInfo getUserInfo(@RequestBody UserInfo userInfo){
        log.info("getUserInfo: {}",userInfo.toString());
        return userInfo;
    }
}
