package com.cxp.springboot2shardingsphere.config.master_slave;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author : cheng
 * @date : 2019-10-27 11:03
 */
public class PreciseModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Integer> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        for (String targetName : availableTargetNames) {
            if (targetName.endsWith(String.valueOf(shardingValue.getValue() % 2 ))){
                return targetName;
            }
        }
        throw new UnsupportedOperationException();
    }
}
