package com.cxp.springbootjpa.dao.repository.manyToMany;

import com.cxp.springbootjpa.entity.manyToMany.AuthorityMany;
import com.cxp.springbootjpa.entity.manyToMany.UserInfoMany;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

/**
 * @author 程
 * @date 2019/3/31 下午4:43
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserInfoManyDaoTest {

    @Autowired
    private UserInfoManyRepository userInfoManyRepository;

    @Autowired
    private AuthorityManyRepository authorityManyRepository;

    @Test
    public void save(){
        AuthorityMany authority1 = new AuthorityMany();
        authority1.setAuthorityId("AU001");
        authority1.setName("超级管理员");

        AuthorityMany authority2 = new AuthorityMany();
        authority2.setAuthorityId("AU002");
        authority2.setName("普通管理员");

        AuthorityMany authority3 = new AuthorityMany();
        authority3.setAuthorityId("AU003");
        authority3.setName("一般用户");

        authorityManyRepository.save(authority1);
        authorityManyRepository.save(authority2);
        authorityManyRepository.save(authority3);

        UserInfoMany userInfo1 = new UserInfoMany();
        userInfo1.setPassword("6666666");
        userInfo1.setUsername("6666666");

        UserInfoMany userInfo2 = new UserInfoMany();
        userInfo2.setPassword("1111111");
        userInfo2.setUsername("1111111");

        userInfo1.getAuthorList().add(authority1);
        userInfo1.getAuthorList().add(authority2);
        userInfo1.getAuthorList().add(authority3);

        userInfo2.getAuthorList().add(authority2);
        userInfo2.getAuthorList().add(authority3);

        userInfoManyRepository.save(userInfo1);
        userInfoManyRepository.save(userInfo2);
    }

    @Test
    public void query(){
        Optional<UserInfoMany> userInfoMany = userInfoManyRepository.findById(3L);
        System.out.println(userInfoMany.get());

        List<UserInfoMany> userInfoManies = userInfoManyRepository.queryUserInfoManys(null);
        System.out.println(userInfoManies);
    }

    @Test
    public void delete(){
        /**
         * 会删除UserInfoMany表中id为2的记录，删除userinfomany_authoritymany表中user_id为2的数据
         */
        userInfoManyRepository.deleteById(2L);
    }

}