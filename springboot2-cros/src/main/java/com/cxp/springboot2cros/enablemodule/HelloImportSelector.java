package com.cxp.springboot2cros.enablemodule;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @program: SpringBoot_Project
 * @description:
 * @author: cheng
 * @create: 2019-10-13 12:01
 */
public class HelloImportSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableHello.class.getName());
        EnableEnum value = (EnableEnum) attributes.get("value");
        switch (value){
            case HELLO1:
                return new String[]{MyEnableHello1Config.class.getName()};
            case HELLO2:
                return new String[]{MyEnableHello2Config.class.getName()};
            default:
                return null;
        }
    }
}
