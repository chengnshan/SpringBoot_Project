package com.cxp.springboot2redis.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 程
 * @date 2019/6/29 下午7:46
 */
public class EntityUtils {

    /**
     * 实体类转换成map
     * @param object
     * @return
     */
    public static Map<String,Object> entityToMap(Object object){
        Map<String,Object> map = new HashMap<>();
        if (object != null){
            for (Field filed : object.getClass().getDeclaredFields()){
                try {
                    boolean accessible = filed.isAccessible();
                    filed.setAccessible(true);
                    Object filedValue = filed.get(object);

                    map.put(filed.getName(),filedValue);
                    filed.setAccessible(accessible);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return map;
    }

    /**
     * Map转实体类
     * @param map 需要初始化的数据，key字段必须与实体类的成员名字一样，否则赋值为空
     * @param entity  需要转化成的实体类
     * @return
     */
    public static <T> T mapToEntity(Map<String, Object> map, Class<T> entity) {
        T t = null;
        try {
            t = entity.newInstance();
            for(Field field : entity.getDeclaredFields()) {
                if (map.containsKey(field.getName())) {
                    boolean flag = field.isAccessible();
                    field.setAccessible(true);
                    Object object = map.get(field.getName());
                    if (object!= null && field.getType().isAssignableFrom(object.getClass())) {
                        field.set(t, object);
                    }
                    field.setAccessible(flag);
                }
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }
}
