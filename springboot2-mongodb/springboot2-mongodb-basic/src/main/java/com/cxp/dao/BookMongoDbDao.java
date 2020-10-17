package com.cxp.dao;

import com.cxp.pojo.Book;

import java.util.List;

/**
 * @author : cheng
 * @date : 2020-06-21 22:10
 */
public interface BookMongoDbDao {

    List<Book> list();

}
