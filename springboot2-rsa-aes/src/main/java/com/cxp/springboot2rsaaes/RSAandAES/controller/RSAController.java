package com.cxp.springboot2rsaaes.RSAandAES.controller;

import com.cxp.springboot2rsaaes.RSAandAES.config.SecurityParameter;
import com.cxp.springboot2rsaaes.RSAandAES.pojo.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 程
 * @date 2019/7/15 上午10:14
 */
@Controller
public class RSAController {

    /**
     * 跳转RSA+AES双重加密页面
     * @return
     */
    @RequestMapping("/toRSAAndAESPage")
    public String toRSAAndAESPage() {
        return "RSAAndAES";
    }

    /**
     * RSA+AES双重加密测试
     *
     * @return object
     */
    @RequestMapping("/testEncrypt")
    @ResponseBody
    @SecurityParameter
    public UserInfo testEncrypt(@RequestBody UserInfo userInfo) {
        System.out.println(userInfo.getUsername());
        String content = "服务端返回内容";
        userInfo.setUsername(content);
        return userInfo;
    }
}
