package com.cxp.springbootthymeleaf.controller;

import com.cxp.springbootthymeleaf.Book;
import com.cxp.springbootthymeleaf.utils.Base64AndImageUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author 程
 * @date 2019/6/8 下午1:58
 */
@Controller
public class ThymeleafController {

    @RequestMapping(value = "/toThymeleafPage.html")
    public String toThymeleafPage(ModelMap map){
        map.put("name","大鹏");

        /*输出对象*/
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("birthday",new Date());
        paramMap.put("address","广东深圳");
        map.put("paramMap",paramMap);

        /*输出集合list*/
        List<Book> list = new ArrayList<>();
        list.add(new Book(1,"数学",new BigDecimal(19)));
        list.add(new Book(2,"语文",new BigDecimal(29)));
        list.add(new Book(3,"英语",new BigDecimal(39)));
        map.put("list",list);


        return "thymeleaf";
    }

    @RequestMapping(value = "/toUploadImageByBase64.html")
    public String toUploadImageByBase64(ModelMap map){

        String base64 = Base64AndImageUtil.imageToBase64("/Volumes/Passport_2/学习练习文件/微信小程序/001-helloworld/images/timg.jpg");
        map.put("base64",base64);

        return "uploadImage";
    }
}
