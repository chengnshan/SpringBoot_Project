package com.cxp.springboot2jasypt.config;

import org.jasypt.encryption.StringEncryptor;

/**
 * 自定义加解密
 * @author 程
 * @date 2019/7/12 下午10:17
 */
public class C9Encryptor implements StringEncryptor{

    /**
     * 加密的方法
     * @param message
     * @return
     */
    @Override
    public String encrypt(String message) {
        return null;
    }

    /**
     * 解密的方法
     * @param encryptedMessage
     * @return
     */
    @Override
    public String decrypt(String encryptedMessage) {
        return null;
    }
}
