package com.cxp.springboot2cros.read_config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @author : cheng
 * @date : 2020-03-22 22:30
 */
@Component
@PropertySource(value = {"classpath:config/config1.properties","classpath:config/config2.properties"},encoding = "UTF-8")
@ConfigurationProperties(prefix = "config")
@Data
@ToString
public class PropertySourceConfig {

    private String url;
    private String username;
    private String password;
}
