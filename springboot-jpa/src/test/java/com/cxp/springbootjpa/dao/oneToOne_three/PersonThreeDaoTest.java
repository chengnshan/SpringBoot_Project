package com.cxp.springbootjpa.dao.oneToOne_three;

import com.cxp.springbootjpa.entity.oneToOne_three.AddressThree;
import com.cxp.springbootjpa.entity.oneToOne_three.PersonThree;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author 程
 * @date 2019/3/31 下午12:37
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class PersonThreeDaoTest {

    @Autowired
    private PersonThreeDao personThreeDao;

    @Autowired
    private AddressThreeDao addressThreeDao;

    @Test
    public void save(){
        PersonThree personThree = new PersonThree();
        personThree.setBirthday(new Timestamp(Calendar.getInstance().getTime().getTime()));
        personThree.setName("张三丰");
        personThree.setSex("男");

        AddressThree addressThree = new AddressThree();
        addressThree.setAddress("广东深圳");
        addressThree.setPhone("13409876529");
        addressThree.setZipcode("9012");
        personThree.setAddressThree(addressThree);

        personThreeDao.save(personThree);
    }

    @Test
    public void query(){
        List<PersonThree> personThrees = personThreeDao.findAll();
        personThrees.forEach(personThree -> {
            System.out.println(personThree);
        });
    }

    @Test
    public void delete(){
        personThreeDao.deleteById(1L);
    }
}