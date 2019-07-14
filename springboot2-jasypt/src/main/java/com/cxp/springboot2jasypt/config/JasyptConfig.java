package com.cxp.springboot2jasypt.config;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyResolver;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.StandardPBEByteEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 程
 * @date 2019/7/13 下午7:50
 */
@Configuration
public class JasyptConfig {

    @Value("${jasypt.encryptor.password}")
    private String jasyptPwd;

    @Bean(name = "pooledPBEStringEncryptor")
    public StringEncryptor stringEncryptor(){
        PooledPBEStringEncryptor pooledPBEStringEncryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        config.setPassword(jasyptPwd);
        config.setAlgorithm(StandardPBEByteEncryptor.DEFAULT_ALGORITHM);
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");
        pooledPBEStringEncryptor.setConfig(config);
        return pooledPBEStringEncryptor;
    }

   // @Bean(name = "C9DefaultPropertyDetector")
    public EncryptablePropertyDetector propertyDetector(){
        return new C9DefaultPropertyDetector();
    }

}
