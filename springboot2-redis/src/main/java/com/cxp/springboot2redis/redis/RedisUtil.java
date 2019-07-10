package com.cxp.springboot2redis.redis;

import com.cxp.springboot2redis.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author 程
 * @date 2019/6/22 下午9:11
 */
@Component
public class RedisUtil {

    private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);

    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired
    @Qualifier(value = "redisTemplateJson")
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate){
        RedisUtil.redisTemplate = redisTemplate;
    }

    //=============================common============================
    /**
     * 指定缓存失效时间
     * @param key 键
     * @param time 时间(秒)
     * @return
     */
    public static boolean expire(String key,long time){
        try{
            if (time > 0 ){
                redisTemplate.expire(key,time, TimeUnit.SECONDS);
            }
            return true;
        }catch (Exception e){
            logger.error("expire exception: "+e.getMessage(),e);
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public static long getExpire(String key){
        return redisTemplate.getExpire(key,TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return true 存在 false不存在
     */
    public static boolean hasKey(String key){
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除缓存
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public static boolean delete(String ... key){
        if(key!=null && key.length>0){
            if(key.length==1){
                return redisTemplate.delete(key[0]);
            }else{
                Long deleteNum = redisTemplate.delete(CollectionUtils.arrayToList(key));
                return deleteNum != null && deleteNum.equals(key.length);
            }
        }
        return false;
    }

    //=============================string============================
    /**
     * 普通缓存获取
     * @param key 键
     * @return 值
     */
    public static Object get(String key){
        return key==null?null:redisTemplate.opsForValue().get(key);
    }

    /**
     * 普通缓存放入
     * @param key 键
     * @param value 值
     * @return true成功 false失败
     */
    public static boolean set(String key,Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


    /**
     *
     * @param key
     * @param value
     * @param time  秒
     * @return
     */
    public static boolean setIfAbsent(String key,String value,long time){
        try {
            return redisTemplate.opsForValue().setIfAbsent(key,value, Duration.ofSeconds(time));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     *  为多个键分别设置值,如果存在则返回false，不存在返回true
     * @param map
     * @return
     */
    public static boolean setIfAbsentMulti(Map<String,String> map){
        try{
            return redisTemplate.opsForValue().multiSetIfAbsent(map);
        }catch (Exception e){
            logger.error("setIfAbsentMulti exception: "+e.getMessage(),e);
            return false;
        }
    }

    /**
     * 递增
     * @param key 键
     * @param
     * @return
     */
    public static long incr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 递减
     * @param key 键
     * @param
     * @return
     */
    public static long decr(String key, long delta){
        if(delta<0){
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    //================================Hash=================================
    /**
     * HashGet
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public static Object hget(String key,String item){
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     * @param key 键
     * @return 对应的多个键值
     */
    public static Map<Object,Object> hmget(String key){
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 根据一个hash类型的key获取得到一个对象
     * @param key
     * @param clazz
     * @return
     */
    public static <T> T hgetObj(String key,Class<T> clazz){
        Map<Object, Object> map = hmget(key);
        Map<String,Object> oMap = new HashMap<>();
        for (Map.Entry<Object, Object> param : map.entrySet()){
            String hKey = String.valueOf(param.getKey());
            oMap.put(hKey,param.getValue());
        }
        return EntityUtils.mapToEntity(oMap,clazz);
    }
    /**
     * HashSet
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public static boolean hmset(String key, Map<String,Object> map){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            logger.error("hmset exception: "+e.getMessage(),e);
            return false;
        }
    }

    public static boolean hsetObj(String key, Object object){
        if (object != null){
            Map<String, Object> map = EntityUtils.entityToMap(object);
            return hmset(key,map);
        }
        return false;
    }

    /**
     * HashSet 并设置时间
     * @param key 键
     * @param map 对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public static boolean hmset(String key, Map<String,Object> map, long time){
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @return true 成功 false失败
     */
    public static boolean hset(String key,String item,Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     * @param key 键
     * @param item 项
     * @param value 值
     * @param time 时间(秒)  注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public static boolean hset(String key,String item,Object value,long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if(time>0){
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     * @param key 键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public static void hdel(String key, Object... item){
        redisTemplate.opsForHash().delete(key,item);
    }

    /**
     * 判断hash表中是否有该项的值
     * @param key 键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public static boolean hHasKey(String key, String item){
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     * @param key 键
     * @param item 项
     * @param by 要增加几(大于0)
     * @return
     */
    public static double hincr(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递减
     * @param key 键
     * @param item 项
     * @param by 要减少记(小于0)
     * @return
     */
    public static double hdecr(String key, String item,double by){
        return redisTemplate.opsForHash().increment(key, item,-by);
    }

    //============================set=============================
    /**
     * 根据key获取Set中的所有值
     * @param key 键
     * @return
     */
    public static Set<Object> sGet(String key){
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     * @param key 键
     * @param value 值
     * @return true 存在 false不存在
     */
    public static boolean sHasKey(String key,Object value){
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存
     * @param key 键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSet(String key, Object...values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 将set数据放入缓存
     * @param key 键
     * @param time 时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public static long sSetAndTime(String key,long time,Object...values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if(time>0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 获取set缓存的长度
     * @param key 键
     * @return
     */
    public static long sGetSetSize(String key){
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     * @param key 键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public static long setRemove(String key, Object ...values) {
        try {
            Long count = redisTemplate.opsForSet().remove(key, values);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

//===============================list=================================
    /**
     * 获取list缓存的内容
     * @param key 键
     * @param start 开始
     * @param end 结束  0 到 -1代表所有值
     * @return
     */
    public static List<Object> lGet(String key, long start, long end){
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     * @param key 键
     * @return
     */
    public static long lGetListSize(String key){
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     * @param key 键
     * @param index 索引  index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     * @return
     */
    public static Object lGetIndex(String key,long index){
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param
     * @return
     */
    public static boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public static boolean lSet(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param
     * @return
     */
    public static boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     * @param key 键
     * @param value 值
     * @param time 时间(秒)
     * @return
     */
    public static boolean lSet(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     * @param key 键
     * @param index 索引
     * @param value 值
     * @return
     */
    public static boolean lUpdateIndex(String key, long index,Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count 移除多少个
     * @param value 值
     * @return 移除的个数
     */
    public static long lRemove(String key,long count,Object value) {
        try {
            Long remove = redisTemplate.opsForList().remove(key, count, value);
            return remove;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除N个值为value
     * @param key 键
     * @param count
     * @param values 值
     * @return 移除的个数
     */
    public static long lRemoveList(String key,long count,List<Object> values) {
        int result = 0;
        try {
            if (!CollectionUtils.isEmpty(values)){
                for (Object obj: values) {
                    lRemove(key,count,obj);
                    result++;
                }
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //===============================ZSet=================================

    /**
     *  添加一个值到sortset
     * @param key
     * @param value
     * @param score
     * @return  存在的话为false，不存在的话为true
     */
    public static boolean zsetAdd(String key,String value,double score){
        try{
            return redisTemplate.opsForZSet().add(key,value,score);
        }catch (Exception e){
            logger.error("zsetAdd exception: "+e.getMessage(),e);
            return false;
        }
    }

    /**
     * 获取zset类型键为key，从start到end的所有有序值
     * @param key
     * @param start
     * @param end
     * @return
     */
    public static Set<Object> zsetRang(String key,long start,long end){
        try{
            return redisTemplate.opsForZSet().range(key,start,end);
        }catch (Exception e){
            logger.error("zsetRang exception: "+e.getMessage(),e);
        }
        return null;
    }

    /**
     *  获取值为memeber对应的score
     * @param key
     * @param member
     * @return
     */
    public static Double zsetGetScore(String key,String member){
        try{
            return redisTemplate.opsForZSet().score(key,member);
        }catch (Exception e){
            logger.error("zsetRang exception: "+e.getMessage(),e);
        }
        return null;
    }

    /**
     * 获取key中score在min和max范围之间的值
     * @param key
     * @param min
     * @param max
     * @return
     */
    public static Set<Object> zsetRangByScore(String key,double min,double max){
        try{
            return redisTemplate.opsForZSet().rangeByScore(key,min,max);
        }catch (Exception e){
            logger.error("zsetRangByScore exception: "+e.getMessage(),e);
        }
        return null;
    }

    public static Set<Object> zsetRangByScore(String key,double min,double max,long offset, long count){
        try{
            return redisTemplate.opsForZSet().rangeByScore(key,min,max,offset,count);
        }catch (Exception e){
            logger.error("zsetRangByScore exception: "+e.getMessage(),e);
        }
        return null;
    }

    /**
     * 移除sortset中的值
     * @param key
     * @param value
     * @return
     */
    public static long zsetRemove(String key,String value){
        try{
            return redisTemplate.opsForZSet().remove(key,value);
        }catch (Exception e){
            logger.error("zsetRang exception: "+e.getMessage(),e);
        }
        return 0;
    }
}
