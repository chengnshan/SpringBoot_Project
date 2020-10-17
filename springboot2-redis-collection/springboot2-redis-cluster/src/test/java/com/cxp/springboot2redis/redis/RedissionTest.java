package com.cxp.springboot2redis.redis;

import org.junit.Test;


import java.util.concurrent.TimeUnit;

/**
 * @author 程
 * @date 2019/7/11 下午9:38
 */
public class RedissionTest {

    @Test
    public void test1() throws Exception{
        String content = "{" +
                "   \"singleServerConfig\":{" +
                "      \"idleConnectionTimeout\":10000," +
                "      \"pingTimeout\":1000," +
                "      \"connectTimeout\":10000," +
                "      \"timeout\":3000," +
                "      \"retryAttempts\":3," +
                "      \"retryInterval\":1500,\n" +
                "      \"reconnectionTimeout\":3000," +
                "      \"failedAttempts\":3," +
                "      \"password\":\"123456\"," +
                "      \"subscriptionsPerConnection\":5," +
                "      \"clientName\":null," +
                "      \"address\": \"redis://192.168.153.128:6380\"," +
                "      \"subscriptionConnectionMinimumIdleSize\":1," +
                "      \"subscriptionConnectionPoolSize\":10," +
                "      \"connectionMinimumIdleSize\":5," +
                "      \"connectionPoolSize\":5," +
                "      \"database\":0," +
                "      \"dnsMonitoringInterval\":5000" +
                "   }," +
                "   \"threads\":0," +
                "   \"nettyThreads\":0," +
                "   \"codec\":{" +
                "      \"class\":\"org.redisson.codec.JsonJacksonCodec\"" +
                "   }," +
                "   \"transportMode\":\"NIO\"" +
                "}";
    }
}
