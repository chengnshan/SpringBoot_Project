package com.cxp.springboot2quartz.config.dynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * @author : cheng
 * @date : 2020-03-11 17:18
 */

/**
 * 继承Spring AbstractRoutingDataSource实现路由切换
 * Created by ASUS on 2018/4/17.
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        log.info("数据源为:===={}", DataSourceContextHolder.getDB());
        return DataSourceContextHolder.getDB();
    }

}