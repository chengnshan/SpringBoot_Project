package com.cxp.service;

import com.cxp.pojo.Book;

import java.util.List;

/**
 * @author : cheng
 * @date : 2020-06-21 20:08
 */
public interface BookMongoDbService {

    String saveObj(Book book) ;

    List<Book> list(Book book);

}
