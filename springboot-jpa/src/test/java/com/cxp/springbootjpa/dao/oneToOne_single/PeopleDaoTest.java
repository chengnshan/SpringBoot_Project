package com.cxp.springbootjpa.dao.oneToOne_single;

import com.cxp.springbootjpa.dao.oneToOne_double.IcarDoubleDao;
import com.cxp.springbootjpa.dao.oneToOne_double.PeopleDoubleDao;
import com.cxp.springbootjpa.entity.oneToOne_double.Icard_Double;
import com.cxp.springbootjpa.entity.oneToOne_double.People_Double;
import com.cxp.springbootjpa.entity.oneToOne_single.Icard;
import com.cxp.springbootjpa.entity.oneToOne_single.People;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/3/24 上午11:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PeopleDaoTest {

    @Autowired
    private PeopleDao peopleDao;

    @Autowired
    @Qualifier(value = "json_redistemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier(value = "byte_redistemplate")
    private RedisTemplate byteRedisTemplate;

    @Test
    public void contextLoads() {
        Icard icard = new Icard(null,"123456789012345678");
//
//        People people=new People();
//        people.setBirthday(new Date());
//        people.setPeopleName("张老师");
//        people.setAddress("广东深圳");
//        people.setIcard(icard);
//
//        peopleDao.save(people);
        byteRedisTemplate.opsForValue().set("icard",icard);
        System.out.println(byteRedisTemplate.opsForValue().get("icard"));
        System.out.println(peopleDao.findAll());
    }


}