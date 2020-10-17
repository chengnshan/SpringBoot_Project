package com.cxp.springboot2redis.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import javax.annotation.PreDestroy;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author 程
 * @date 2019/6/22 下午8:33
 */
@Configuration
@EnableConfigurationProperties(value = {RedisProperties.class})
//@AutoConfigureAfter(value = {RedisAutoConfiguration.class})
public class RedisConfig {

    @Autowired
    private RedisProperties redisProperties;

    /**
     * @description 自定义的缓存key的生成策略
     *              若想使用这个key  只需要讲注解上keyGenerator的值设置为keyGenerator即可</br>
     * @return 自定义策略生成的key
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuffer sb = new StringBuffer();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for(Object obj:params){
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(){
        RedisClusterConfiguration redisConfiguration = new RedisClusterConfiguration();
        List<String> nodes = redisProperties.getCluster().getNodes();
        List<RedisNode> redisNodes = new ArrayList<>();
        nodes.forEach( t -> {
            StringTokenizer tokenizer = new StringTokenizer(t,":");
            String host = tokenizer.nextToken();
            String port = tokenizer.nextToken();
            RedisNode node = new RedisNode(host,Integer.parseInt(port));
            redisNodes.add(node);
        });
        redisConfiguration.setClusterNodes(redisNodes);
        redisConfiguration.setPassword(redisProperties.getPassword());
        redisConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());

        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(redisProperties.getLettuce().getPool().getMaxIdle());
        poolConfig.setMaxTotal(redisProperties.getLettuce().getPool().getMaxActive());
        poolConfig.setMinIdle(redisProperties.getLettuce().getPool().getMinIdle());
        poolConfig.setMaxWaitMillis(redisProperties.getLettuce().getPool().getMaxWait().toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(redisProperties.getLettuce().getPool().getTimeBetweenEvictionRuns().toMillis());
        LettucePoolingClientConfiguration lettucePoolingClientConfiguration = LettucePoolingClientConfiguration
                .builder().poolConfig(poolConfig).build();

        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisConfiguration,lettucePoolingClientConfiguration);
        factory.setDatabase(redisProperties.getDatabase());
        factory.afterPropertiesSet();
        return factory;
    }

    /**缓存管理器*/
    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory redisConnectionFactory) {
        return RedisCacheManager.create(redisConnectionFactory);
    }

    /**
     * 配置自定义redisTemplate
     * @AutoConfigureAfter(RedisAutoConfiguration.class) 是让我们这个配置类在内置的配置类之后在配置，
     *  这样就保证我们的配置类生效，并且不会被覆盖配置
     * @return
     */
    @Bean(name = "redisTemplateJson")
    RedisTemplate<String, Object> redisTemplateJson(RedisConnectionFactory redisConnectionFactory) {

        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
        Jackson2JsonRedisSerializer serializer = new Jackson2JsonRedisSerializer(Object.class);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        serializer.setObjectMapper(mapper);

        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }


    /**
     * 序列化对象
     * @param redisConnectionFactory
     * @return
     */
    @Bean(value = "redisTemplate")
    public RedisTemplate<String,Object> redisTemplateObj(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);

        RedisObjectSerializer1 serializer = new RedisObjectSerializer1();
        template.setValueSerializer(serializer);
        //使用StringRedisSerializer来序列化和反序列化redis的key值
        template.setKeySerializer(new StringRedisSerializer());
        template.setHashKeySerializer(new StringRedisSerializer());
        template.setHashValueSerializer(serializer);
        template.afterPropertiesSet();
        return template;
    }
}
