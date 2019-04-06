package com.cxp.springbootjpa.dao.repository.oneToOne_double;

import com.cxp.springbootjpa.entity.oneToOne_double.Icard_Double;
import com.cxp.springbootjpa.entity.oneToOne_double.People_Double;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * @author 程
 * @date 2019/3/25 下午10:10
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PeopleDoubleDaoTest {

    @Autowired
    private PeopleDoubleRepository peopleDoubleDao;
    @Autowired
    private IcarDoubleRepository icarDoubleDao;

    @Test
    public void test1() {
        Icard_Double icard_double = new Icard_Double();
        icard_double.setPid("323456789012345678");
        icard_double.setPName("李四");
        icarDoubleDao.save(icard_double);
        People_Double peopleDouble = new People_Double(null,"一对一双向外键关联",
                "一对一双向外键关联",new Date(),icard_double);
        peopleDoubleDao.save(peopleDouble);
    }

    @Test
    public void test2() {
        List<Icard_Double> icard_doubles = icarDoubleDao.findAll();
        System.out.println(icard_doubles.get(0).getPeople_double());

        System.out.println(icarDoubleDao.findById("323456789012345678"));
    }
}