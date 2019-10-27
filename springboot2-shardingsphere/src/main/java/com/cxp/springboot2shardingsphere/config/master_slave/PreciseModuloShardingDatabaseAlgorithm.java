package com.cxp.springboot2shardingsphere.config.master_slave;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author : cheng
 * @date : 2019-10-27 11:02
 */
public class PreciseModuloShardingDatabaseAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        for (String availableTargetName : availableTargetNames) {
            if (availableTargetName.endsWith(String.valueOf(shardingValue.getValue() % 2))){
                return availableTargetName;
            }
        }
        throw new UnsupportedOperationException();
    }
}
