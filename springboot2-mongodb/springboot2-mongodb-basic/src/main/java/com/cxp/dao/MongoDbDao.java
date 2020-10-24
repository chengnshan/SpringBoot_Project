package com.cxp.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * @author : cheng
 * @date : 2020-06-21 20:00
 */
public abstract class MongoDbDao<T> {

    protected Logger logger = LoggerFactory.getLogger(MongoDbDao.class);

    @Autowired
    protected MongoTemplate mongoTemplate;

    /**
     * 反射获取泛型类型
     *
     * @return
     */
    protected abstract Class<T> getEntityClass();

    /***
     * 保存一个对象
     * @param t
     */
    public void save(T t) {
        logger.info("-------------->MongoDB save start");
        this.mongoTemplate.save(t);
    }

    public long deleteByProperty(T t){
        Query query = getQueryByObject(t);
        return this.mongoTemplate.remove(query,this.getEntityClass()).getDeletedCount();
    }

    public long deleteById(String id){
        Query query = new Query(Criteria.where("_id").is(id));
        return this.mongoTemplate.remove(query,this.getEntityClass()).getDeletedCount();
    }

    /***
     * 根据id从几何中查询对象
     * @param id
     * @return
     */
    public T queryById(Integer id) {
        Query query = new Query(Criteria.where("_id").is(id));
        logger.info("-------------->MongoDB find by id start");
        return this.mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 根据条件查询集合
     *
     * @param object
     * @return
     */
    public List<T> queryList(T object) {
        Query query = getQueryByObject(object);
        logger.info("-------------->MongoDB find list start");
        return mongoTemplate.find(query, this.getEntityClass());
    }

    /**
     * 根据条件查询只返回一个文档
     *
     * @param object
     * @return
     */
    public T queryOne(T object) {
        Query query = getQueryByObject(object);
        logger.info("-------------->MongoDB find one start");
        return mongoTemplate.findOne(query, this.getEntityClass());
    }

    /**
     * 将查询条件对象转换为query
     *
     * @param object
     * @return
     * @author Jason
     */
    private Query getQueryByObject(T object) {
        Query query = new Query();
        String[] fileds = getFiledName(object);
        Criteria criteria = new Criteria();
        for (int i = 0; i < fileds.length; i++) {
            String filedName = (String) fileds[i];
            Object filedValue = getFieldValueByName(filedName, object);
            if (filedValue != null) {
                criteria.and(filedName).is(filedValue);
            }
        }
        query.addCriteria(criteria);
        return query;
    }

    /***
     * 获取对象属性返回字符串数组
     * @param o
     * @return
     */
    private static String[] getFiledName(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        String[] fieldNames = new String[fields.length];

        for (int i = 0; i < fields.length; ++i) {
            fieldNames[i] = fields[i].getName();
        }

        return fieldNames;
    }

    /***
     * 根据属性获取对象属性值
     * @param fieldName
     * @param o
     * @return
     */
    private static Object getFieldValueByName(String fieldName, Object o) {
        try {
            String e = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + e + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[0]);
            return method.invoke(o, new Object[0]);
        } catch (Exception var6) {
            return null;
        }
    }
}