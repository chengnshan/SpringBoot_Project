package com.cxp.springboot2rsaaes.jsonview.controller;

import com.cxp.springboot2rsaaes.jsonview.pojo.UserInfo;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 程
 * @date 2019/7/14 上午11:22
 */
@RestController
public class UserController {

    @GetMapping("/user")
    @JsonView({UserInfo.UserSimpleView.class})
    public List<UserInfo> query(UserInfo userInfo,
                                @PageableDefault(size = 10,page=1,sort = {"username","age"},
                                        direction = Sort.Direction.DESC)
                                        Pageable pageable){
        System.out.println(userInfo);
        System.out.println(pageable.getPageSize());
        System.out.println(pageable.getSort());
        System.out.println(pageable.getOffset());
        List<UserInfo> users = new ArrayList<>();
        users.add(new UserInfo("张三","321",12));
        users.add(new UserInfo("李四","123",32));
        users.add(new UserInfo("王五","345",22));
        return users;
    }

    /**
     * 在url中使用正则表达式
     * @param id
     * @return
     */
    @GetMapping("/user/{id:\\d+}")
    @JsonView(UserInfo.UserDetailView.class)
    public UserInfo get(@PathVariable String id){
        System.out.println(id);
        UserInfo user = new UserInfo();
        user.setUsername("tom");
        user.setPassword("tom123");
        return user;
    }
}
