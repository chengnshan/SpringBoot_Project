package com.cxp.dao.impl;

import com.cxp.dao.MongoDbDao;
import com.cxp.pojo.Book;
import org.springframework.stereotype.Repository;

/**
 * @author : cheng
 * @date : 2020-06-21 20:07
 */
@Repository
public class BookMongoDbDaoImpl extends MongoDbDao<Book> {

    @Override
    protected Class<Book> getEntityClass() {
        return Book.class;
    }
}
