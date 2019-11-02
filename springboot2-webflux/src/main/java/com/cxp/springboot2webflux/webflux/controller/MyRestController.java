package com.cxp.springboot2webflux.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 注解模式
 * @author : cheng
 * @date : 2019-10-27 21:30
 */
@RestController
public class MyRestController {

    @GetMapping(value = {"/getStr/{username}"})
    public Mono<String> getStr(@PathVariable("username")String username){
        return Mono.just("{\"username\":"+username+"}");
    }

    @GetMapping(value = {"/getJson/{username}"})
    public Mono<Map> getJson(@PathVariable("username")String username){
        Map<String,Object> param = new HashMap<>();
        param.put("username",username);
        param.put("age",111);
        return Mono.just(param);
    }

    @GetMapping("/getUserName/{username}")
    public Flux<String> getUserName(@PathVariable String username) {
        return Flux.just("{\"username\":"+username+"}");
    }

}
