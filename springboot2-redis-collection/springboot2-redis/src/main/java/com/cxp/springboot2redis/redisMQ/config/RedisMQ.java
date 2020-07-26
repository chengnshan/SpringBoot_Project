package com.cxp.springboot2redis.redisMQ.config;

import com.cxp.springboot2redis.redis.RedisUtil;
import com.cxp.springboot2redis.redisMQ.pojo.Message;
import com.cxp.springboot2redis.redisMQ.pojo.Route;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author 程
 * @date 2019/7/10 下午3:01
 */
public class RedisMQ {

    /**
     * 消息池前缀，以此前缀加上传递的消息id作为key，以消息{@link Message}
     * 的消息体body作为值存储
     */
    private static final String MSG_POOL = "Message:Pool:";

    /*** 默认监听数量，对应监听zset队列前多少个元素*/
    private static final int DEFAUT_MONITOR = 10;

    /**每次监听queue中元素的数量，可配置*/
    private int monitorCount = DEFAUT_MONITOR;

    /**消息路由*/
    private List<Route> routes;

    /**
     * 存入消息池(string类型存储的)
     * @param message
     * @return
     */
    public boolean addMsgPool(Message message) {
        if (message != null){
           return RedisUtil.setIfAbsent(MSG_POOL + message.getId(), message.getBody(), message.getTtl());
        }
        return false;
    }

    /**
     * 从消息池中删除消息
     * @param id
     * @return
     */
    public boolean deMsgPool(String id) {
        return RedisUtil.delete(MSG_POOL + id);
    }

    /**
     * 向队列中添加消息
     * @param key
     * @param score 优先级
     * @param value
     * @return 返回消息id
     */
    public String enMessage(String key, long score, String value) {

        if (RedisUtil.zsetAdd(key,value,score)) {
            return value;
        }
        return "";
    }

    /**
     * 从队列删除消息
     * @param id
     * @return
     */
    public boolean deMessage(String key, String id) {
        return RedisUtil.zsetRemove(key,id) >0 ?true : false;
    }

    /**
     * 消费消息
     * @param key
     * @return
     */
    public List<String> consume(String key) {
        long count =RedisUtil.lGetListSize(key);
        if (0 < count) {
            // 可根据需求做限制
            List<Object> ids = RedisUtil.lGet(key,0,count-1);
            if (ids != null) {
                List<String> result = new ArrayList<>();
                ids.forEach(object -> {
                    result.add(String.valueOf(RedisUtil.get(MSG_POOL+String.valueOf(object))));
                });
                RedisUtil.lRemoveList(key,1,ids);
                return result;
            }
        }
        return null;
    }

    @Scheduled(cron = "*/5 * * * * *")
    public void monitor(){
        int route_size;
        if (null == routes || 1 > (route_size =routes.size())){
            return;
        }
        String queue, list;
        Set<Object> set;
        for (int i = 0; i < route_size; i++){
            queue = routes.get(i).getQueue();
            list = routes.get(i).getList();
            set = RedisUtil.zsetRang(queue,0,monitorCount);
            if (null != set) {
                long current = System.currentTimeMillis();
                long score;
                for (Object id : set) {
                    score = RedisUtil.zsetGetScore(queue,String.valueOf(id)).longValue();
                    if (current >= score) {
                        // 添加到list
                        if (RedisUtil.lSet(list, String.valueOf(id))) {
                            // 删除queue中的元素
                            deMessage(queue, String.valueOf(id));
                        }
                    }
                }
            }
        }

    }

    public int getMonitorCount() {
        return monitorCount;
    }

    public void setMonitorCount(int monitorCount) {
        this.monitorCount = monitorCount;
    }

    public List<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
}
