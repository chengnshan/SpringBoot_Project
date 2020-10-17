package com.cxp.dao.impl;

import com.cxp.dao.BookMongoDbDao;
import com.cxp.dao.MongoDbDao;
import com.cxp.pojo.Book;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author : cheng
 * @date : 2020-06-21 20:07
 */
@Repository
public class BookMongoDbDaoImpl extends MongoDbDao<Book> implements BookMongoDbDao {

    @Override
    protected Class<Book> getEntityClass() {
        return Book.class;
    }

    @Override
    public List<Book> list() {
        List<Book> books = this.mongoTemplate.findAll(Book.class);
        return books;
    }


}
