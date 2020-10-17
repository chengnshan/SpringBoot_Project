package com.cxp.service.impl;

import com.cxp.dao.impl.BookMongoDbDaoImpl;
import com.cxp.pojo.Book;
import com.cxp.service.BookMongoDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String now = LocalDateTime.now().format(dateTimeFormatter);
        book.setCreateTime(now);
        book.setUpdateTime(now);
        //调用bookMongoDbDao父类中的添加方法
        bookMongoDbDao.save(book);
        return "添加成功";
    }

    @Override
    public List<Book> listByBook(Book book) {
        return bookMongoDbDao.queryList(book);
    }

    @Override
    public List<Book> list() {
        return bookMongoDbDao.list();
    }

    @Override
    public long deleteById(String id) {
        return bookMongoDbDao.deleteById(id);
    }

    @Override
    public long deleteByProperty(Book book) {
        return bookMongoDbDao.deleteByProperty(book);
    }
}
