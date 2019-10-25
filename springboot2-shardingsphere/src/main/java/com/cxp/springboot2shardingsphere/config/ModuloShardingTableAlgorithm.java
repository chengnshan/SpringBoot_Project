package com.cxp.springboot2shardingsphere.config;

import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * 表分片策略
 *
 * 提示:PreciseShardingAlgorithm接口的泛型，视对应分片键的数据类型而定
 *     如:列是字符串，那么这里是 String
 *        列是int，那么这里是 Integer
 *        列是bigint，那么这里是 Long
 *
 * @author: cheng
 * @create: 2019-10-25 22:28
 */
public class ModuloShardingTableAlgorithm implements PreciseShardingAlgorithm<Integer> {

    /**
     * Sharding.
     *
     * @param availableTargetNames
     *            数据源(库)名称集合 或 真实表名称集合。
     *
     *            提示:数据库源一旦定下来了，那么对应有哪些可用的真实表也就随之定下来了。
     *
     *            注:这里为: 数据源(库)名称集合。
     *               如，本人此次示例中，设置了的数据源有otherDb、youngerDb、olderDb，
     *               那么availableTargetNames输出就应为[otherDb, youngerDb, olderDb]
     *
     *            注:因为是先路由定位库，再路由定位真实表;所以一旦库定下来了，那么真实表的集合就定下来了。
     *               以本人的此项目的库与表进行说明:
     *               otherDb库只有表staff.
     *               youngerDb库有表staff_man。staff_woman.
     *               olderDb库有表staff_man。staff_woman.
     *               在进行表分片之前会先进性数据库(数据源)分片，在数据源分片时，就路由定下来了走
     *               otherDb的话，那么这里定位真实表时，候选的真实表集合里只有staff；如果
     *               在数据源分片时，路由定下来了走youngerDb的话，那么这里定位真实表时，候选的
     *               真实表集合里只有staff_man和staff_woman；
     *
     * @param shardingValue
     *            分片键的值。
     *            如:本人此次示例中，路由至哪张表，是由gender为真实表分片键的，
     *               gender的列类型为char,所以这里的泛型是String。
     *               如果分片键的类型是int的话，这里的泛型就应该是Integer.
     *               如果分片键的类型是bigint的话，这里的泛型就应该是Long.
     *               如果分片键的类型是varchar/char的话，这里的泛型就应该是String.
     *
     * @return 路由后的SQL要使用的数据源(库)的名字   或   路由后的SQL要使用的真实表的名字
     *          注:这里为 路由后的SQL要使用的真实表的名字
     */
    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Integer> shardingValue) {
        Integer value = shardingValue.getValue();
        for (String each : availableTargetNames) {
            if (each.endsWith(value % 2 + "")) {
                return each;
            }
        }
        throw new UnsupportedOperationException();
    }

}
