package com.cxp.springboot2cros.read_config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : cheng
 * @date : 2020-03-22 22:30
 */
@Component
@PropertySource(value = {"classpath:config/config1.properties","classpath:config/config2.properties"},encoding = "UTF-8")
@Data
@ToString
public class PropertySourceValue {

    @Value("${config.url}")
    private String url;
    @Value("${config.username}")
    private String username;
    @Value("${config.password}")
    private String password;
}