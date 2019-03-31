package com.cxp.springbootjpa.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.serializer.support.DeserializingConverter;
import org.springframework.core.serializer.support.SerializingConverter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @author 程
 * @date 2019/3/24 下午1:57
 */
@Slf4j
public class RedisConver implements RedisSerializer<Object> {

    //序列化器
    private Converter<Object,byte[]> serializer = new SerializingConverter();
    //反序列化器
    private Converter<byte[],Object> deserializer = new DeserializingConverter();

    /**
     * 将对象序列化成字节数组
     * @param o
     * @return
     * @throws SerializationException
     */
    @Override
    public byte[] serialize(Object o) throws SerializationException {
        if (o==null){
            return new byte[0];
        }
        try {
            return serializer.convert(o);
        }catch (Exception e){
            log.error("RedisConver serialize Exception"+e.getMessage(),e);
        }
        return new byte[0];
    }

    @Override
    public Object deserialize(byte[] bytes) throws SerializationException {
        if (bytes == null || bytes.length == 0 ){
            return null;
        }
        try {
            return deserializer.convert(bytes);
        }catch (Exception e){
            log.error("RedisConver deserialize Exception"+e.getMessage(),e);
        }
        return null;
    }
}
