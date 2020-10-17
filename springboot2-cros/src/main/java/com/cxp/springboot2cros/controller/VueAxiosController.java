package com.cxp.springboot2cros.controller;

import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author : cheng
 * @date : 2020-03-04 14:56
 */
@RestController
public class VueAxiosController {

    @GetMapping(value = "/axiosGet")
    @CrossOrigin
    public String axiosGet(HttpServletRequest request,String name,Integer age){
        Map<String,Object> map = new HashMap<>(8);
        map.put("name",name);
        map.put("code",200);
        map.put("age", new Random().nextInt(100));

        String jsonString = JSON.toJSONString(map);
        return jsonString;
    }
}
