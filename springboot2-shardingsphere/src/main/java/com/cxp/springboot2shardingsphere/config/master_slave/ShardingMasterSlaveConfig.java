package com.cxp.springboot2shardingsphere.config.master_slave;

import com.cxp.springboot2shardingsphere.util.DataSourceUtil;
import com.google.common.collect.Lists;
import org.apache.shardingsphere.api.config.masterslave.MasterSlaveRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.KeyGeneratorConfiguration;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.*;

/**
 * @author : cheng
 * @date : 2019-10-27 10:22
 */
public class ShardingMasterSlaveConfig {

    @Bean
    public DataSource getDataSource() throws SQLException {
        //ShardingRuleConfiguration 分片规则配置对象
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        //tableRuleConfigs  分片规则列表
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration());
        //绑定表规则列表
        shardingRuleConfig.getBindingTableGroups().add("user_info");
        //默认分库策略
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(
                new StandardShardingStrategyConfiguration("id",
                        new PreciseModuloShardingDatabaseAlgorithm()));
        //默认分表策略
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(
                new StandardShardingStrategyConfiguration("age",
                        new PreciseModuloShardingTableAlgorithm()));

        shardingRuleConfig.setMasterSlaveRuleConfigs(getMasterSlaveRuleConfigurations());

        Properties properties = getProperties();
        return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, properties);
    }

    /**
     * KeyGeneratorConfiguration
     * 默认自增列值生成器配置，可自定义或选择内置类型：SNOWFLAKE/UUID/LEAF_SEGMENT,缺省将使用org.apache.shardingsphere.core.keygen.generator.impl.SnowflakeKeyGenerator
     *
     * @return
     */
    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "id");
        return result;
    }

    TableRuleConfiguration getOrderTableRuleConfiguration() {
        TableRuleConfiguration result = new TableRuleConfiguration("user_info", "ds_${0..1}.user_info_${[0, 1]}");
        result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());
        return result;
    }

    /**
     * 获取其余配置信息
     * sql.show 是否开启SQL显示，默认值: false
     * executor.size    工作线程数量，默认值: CPU核数
     * max.connections.size.per.query   每个物理数据库为每次查询分配的最大连接数量。默认值: 1
     * check.table.metadata.enabled     是否在启动时检查分表元数据一致性，默认值: false
     * query.with.cipher.column 当存在明文列时，是否使用密文列查询，默认值: true
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
     * MasterSlaveRuleConfiguration 读写分离规则，缺省表示不使用读写分离
     *      name    读写分离数据源名称
     *      masterDataSourceName    主库数据源名称
     *      slaveDataSourceNames    从库数据源名称列表
     *      loadBalanceAlgorithm    从库负载均衡算法
     * @return
     */
    List<MasterSlaveRuleConfiguration> getMasterSlaveRuleConfigurations() {
        MasterSlaveRuleConfiguration masterSlaveRuleConfig1 = new MasterSlaveRuleConfiguration(
                "ds_0", "demo_ds_master_0",
                Arrays.asList("demo_ds_master_0_slave_0", "demo_ds_master_0_slave_1"));
        MasterSlaveRuleConfiguration masterSlaveRuleConfig2 = new MasterSlaveRuleConfiguration(
                "ds_1", "demo_ds_master_1",
                Arrays.asList("demo_ds_master_1_slave_0", "demo_ds_master_1_slave_1"));
        return Lists.newArrayList(masterSlaveRuleConfig1, masterSlaveRuleConfig2);
    }

    /**
     * 主从数据源配置
     * @return
     */
    Map<String, DataSource> createDataSourceMap() {
        final Map<String, DataSource> result = new HashMap<>();
        result.put("demo_ds_master_0", DataSourceUtil.createDataSource("master1"));
        result.put("demo_ds_master_0_slave_0", DataSourceUtil.createDataSource("sharding1"));
        result.put("demo_ds_master_0_slave_1", DataSourceUtil.createDataSource("sharding1"));
        result.put("demo_ds_master_1", DataSourceUtil.createDataSource("master2"));
        result.put("demo_ds_master_1_slave_0", DataSourceUtil.createDataSource("sharding2"));
        result.put("demo_ds_master_1_slave_1", DataSourceUtil.createDataSource("sharding2"));
        return result;
    }
}
