package com.cxp.springboot2jasypt.config;

import com.ulisesbocchio.jasyptspringboot.EncryptablePropertyDetector;
import org.springframework.util.Assert;

/**
 * 自定义前缀、后缀
 * @author 程
 * @date 2019/7/12 下午10:19
 */
public class C9DefaultPropertyDetector implements EncryptablePropertyDetector {

    private String prefix = "C9(";
    private String suffix = ")";

    public C9DefaultPropertyDetector(){
        Assert.notNull(prefix, "Prefix can't be null");
        Assert.notNull(suffix, "Suffix can't be null");
        this.prefix = prefix;
        this.suffix = suffix;
    }

    @Override
    public boolean isEncrypted(String property) {
        if (property == null) {
            return false;
        }
        final String trimmedValue = property.trim();
        return (trimmedValue.startsWith(prefix) &&
                trimmedValue.endsWith(suffix));
    }

    @Override
    public String unwrapEncryptedValue(String property) {
        return property.substring(
                prefix.length(),
                (property.length() - suffix.length()));
    }
}
