package com.cxp.service.impl;

import com.cxp.dao.impl.BookMongoDbDaoImpl;
import com.cxp.pojo.Book;
import com.cxp.service.BookMongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author : cheng
 * @date : 2020-06-21 20:08
 */
@Service
public class BookMongoDbServiceImpl implements BookMongoDbService {

    @Autowired
    private BookMongoDbDaoImpl bookMongoDbDao;

    /**
     * 保存对象
     *
     * @param book
     * @return
     */
    @Override
    public String saveObj(Book book) {
        book.setCreateTime(new Date());
        book.setUpdateTime(new Date());
        //调用bookMongoDbDao父类中的添加方法
        bookMongoDbDao.save(book);
        return "添加成功";
    }

    @Override
    public List<Book> list(Book book) {
        return bookMongoDbDao.queryList(book);
    }
}
