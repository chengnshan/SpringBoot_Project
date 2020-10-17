package com.cxp.springquartz.config.dynamic;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 一定要增加主数据源，并加上@primary注解，不然业务用的数据源会切换到quartz数据源
 * @author : cheng
 * @date : 2020-03-09 16:55
 */
@Configuration
public class DatasourceConfig {

    /**
     * 主数据源
     * @return
     */
    @Primary
    @Bean(name = "master")
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.master")
    public DruidDataSource masterDataSource(){
        DruidDataSource druidDataSource = DataSourceBuilder.create().type(DruidDataSource.class).build();
        return druidDataSource;
    }

    /**
     * 配置Quartz独立数据源的配置
     */
    @Bean(name = "quartz")
    @QuartzDataSource
    @ConfigurationProperties(prefix = "spring.datasource.dynamic.datasource.quartz")
    public DataSource quartzDataSource(){
        DruidDataSource druidDataSource = new DruidDataSource();
        return druidDataSource;
    }

    @Bean(name = "dynamicDataSource")
    public DataSource dataSource(){
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        // 默认数据源
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
        // 配置多数据源
        Map<Object, Object> dsMap = new HashMap(2);
        dsMap.put("MASTER", masterDataSource());
        dsMap.put("QUARTZ", quartzDataSource());
        dynamicDataSource.setTargetDataSources(dsMap);
        return dynamicDataSource;
    }

    /**
     * 分页插件
     * @return
     */
    @Bean
    public PaginationInterceptor paginationInterceptor(){
        return new PaginationInterceptor();
    }
}
