package com.cxp.springboot2shardingsphere.config;

import com.cxp.springboot2shardingsphere.config.datasharding.ShardingConfig_DataSharding;
import com.cxp.springboot2shardingsphere.config.master_slave.ShardingMasterSlaveConfig;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * @author : cheng
 * @date : 2019-10-27 15:48
 */
public class ShardingSelector implements ImportSelector {

    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        Map<String, Object> attributes = importingClassMetadata.getAnnotationAttributes(EnableSharding.class.getName());
        String value = (String) attributes.get("value");
        switch (value){
            case "dataShardingConfig":
                return new String[]{ShardingConfig_DataSharding.class.getName()};
            case "masterSalveConfig":
                return new String[]{ShardingMasterSlaveConfig.class.getName()};
            default:
                return null;
        }
    }
}
