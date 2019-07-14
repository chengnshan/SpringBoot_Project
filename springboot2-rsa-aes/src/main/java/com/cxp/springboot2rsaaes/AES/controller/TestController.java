package com.cxp.springboot2rsaaes.AES.controller;

import com.cxp.springboot2rsaaes.AES.parameter.SecurityParameter;
import com.cxp.springboot2rsaaes.AES.pojo.UserInfo;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 程
 * @date 2019/7/14 下午5:52
 */
@Controller
@Slf4j
public class TestController {

    @GetMapping(value = "/toAesPage")
    public String toAesPage(){
        return "aes";
    }

    /**
     * 测试返回数据，会自动加密
     * @return
     */
    @GetMapping("/get")
    @ResponseBody
    @SecurityParameter
    public Object get() {
        UserInfo info = new UserInfo();
        info.setUsername("好看");
        return info;
    }

    /**
     * 自动解密，并将返回信息加密
     * @param userInfo
     * @return
     */
    @RequestMapping("/save")
    @ResponseBody
    @SecurityParameter(outEncode = false,inDecode = true)
    @JsonView(UserInfo.UserDetailView.class)
    public Object save(@RequestBody UserInfo userInfo) {
        log.info("TestController save : {}",userInfo);
        return userInfo;
    }
}
