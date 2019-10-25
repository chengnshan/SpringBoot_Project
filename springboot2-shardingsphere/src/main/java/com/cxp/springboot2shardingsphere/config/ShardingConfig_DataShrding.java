package com.cxp.springboot2shardingsphere.config;


import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.ComplexShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.InlineShardingStrategyConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * 数据分片
 *
 * ShardingConfig：核心sharding分片配置类。该类中的主要配置步骤是：
 * 第一步： 获取众多数据源。
 * 第二步： 获取总配置类。
 * 第三步： 获取其余配置信息(如果需要的话)。
 * 第四步：定制指定逻辑表的切片(分库分表)策略。
 * 第五步：将定制了自己的切片策略的表的配置规则，加入总配置中。
 * 第六步：创建并返回sharding总数据源，注入容器。
 *
 * @author: cheng
 * @create: 2019-10-21 22:44
 */
@Configuration
public class ShardingConfig_DataShrding {

    /**
     * 切片配置
     *
     * @return  数据源
     * @date 2019/5/30 21:04
     */
    @Bean
    public DataSource shardingCustomer() throws SQLException {
        // 第一步: 获取众多数据源
        Map<String, DataSource> dataSourceMap = getDatasourceMap();
        // 第二步: 获取总配置类
        ShardingRuleConfiguration shardingRuleConfig = getShardingRuleConfiguration();
        // 第三步: 获取其余配置信息(如果需要的话)
        Properties properties = getProperties();
        // 第六步: 创建并返回sharding总数据源,注入容器
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, shardingRuleConfig, properties);
    }

    /**
     * 获取其余配置信息
     *
     * @return 其余配置信息
     * @date 2019/6/4 11:24
     */
    private Properties getProperties() {
        Properties properties = new Properties();
        // 打印出分库路由后的sql
        properties.setProperty("sql.show", "true");
        return properties;
    }
    /**
     * 获取切片总配置类
     *
     * @return 总配置类
     * @date 2019/6/4 11:24
     */
    private ShardingRuleConfiguration getShardingRuleConfiguration() {
        // sharding总配置类
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        // 设置全局默认库
        shardingRuleConfig.setDefaultDataSourceName("das1");

        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        shardingRuleConfig.getBindingTableGroups().add("user");

        // 设置全局默认分库的策略(本人这里采用：精确分片算法实现的标准分片策略)
        // 如果某个表，没有指定分库策略的话，那么会默认使用这个策略;如果某个表制定了自己的策略，那么就会走自己的策略不走这个默认策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
                new InlineShardingStrategyConfiguration("id","das$->{id % 2}"));

        //设置分表策略
        StandardShardingStrategyConfiguration strategyConfiguration = new StandardShardingStrategyConfiguration(
                "age", new ModuloShardingTableAlgorithm());
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(strategyConfiguration);

        // 设置全局默认分表的策略(本人这里采用：复合分片策略)
        // 如果某个表，没有指定分表策略的话，那么会默认使用这个策略;如果某个表制定了自己的策略，那么就会走自己的策略不走这个默认策略
//        ComplexShardingStrategyConfiguration complexShardingStrategyConfiguration = new ComplexShardingStrategyConfiguration(
//                "age, gender", new StaffTableComplexKeysShardingAlgorithm());
//        shardingRuleConfig.setDefaultTableShardingStrategyConfig(complexShardingStrategyConfiguration);
        return shardingRuleConfig;
    }

    public TableRuleConfiguration getOrderTableRuleConfiguration(){
        TableRuleConfiguration result = new TableRuleConfiguration("user", "das${0..1}.user_${0..1}");
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }

    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id");
        return result;
    }

    /**
     * 获取数据源Map
     *
     * @return  获取数据源Map
     * @date 2019/6/4 10:06
     */
    private Map<String, DataSource> getDatasourceMap() {
        // 真实数据源map
        Map<String, DataSource> dataSourceMap = new HashMap<>(4);

        // 配置第一个数据源,对应库other
        DruidDataSource dataSourceDefault = new DruidDataSource();
        dataSourceDefault.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceDefault.setUrl("jdbc:mysql://192.168.8.101:3306/shrding1?characterEncoding=utf8&serverTimezone=GMT%2B8");
        dataSourceDefault.setUsername("root");
        dataSourceDefault.setPassword("123456");
        dataSourceMap.put("das0", dataSourceDefault);

        // 配置第二个数据源,对应库younger
        DruidDataSource dataSourceYounger = new DruidDataSource();
        dataSourceYounger.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSourceYounger.setUrl("jdbc:mysql://192.168.8.101:3306/shrding2?characterEncoding=utf8&serverTimezone=GMT%2B8");
        dataSourceYounger.setUsername("root");
        dataSourceYounger.setPassword("123456");
        dataSourceMap.put("das1", dataSourceYounger);

        return dataSourceMap;
    }

}
