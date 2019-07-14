package com.cxp.springboot2rsaaes.AES.pojo;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 程
 * @date 2019/7/14 上午11:21
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserInfo {

    /**
     * 用户简单视图
     */
    public interface UserSimpleView{};

    /**
     * 用户详情视图
     */
    public interface UserDetailView extends UserSimpleView{};

    private String username;

    private String password;

    private Integer age;

    @JsonView(UserSimpleView.class)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonView(UserDetailView.class)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @JsonView(UserSimpleView.class)
    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
