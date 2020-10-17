package com.cxp.controller;

import com.cxp.pojo.Book;
import com.cxp.service.BookMongoDbService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * @author : cheng
 * @date : 2020-06-21 20:11
 */
@RestController
public class BookMongodbController {

    @Autowired
    private BookMongoDbService bookMongoDbService;

    @RequestMapping(value = "/book/saveObj", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public String saveObj(@RequestBody Book book) {
        return bookMongoDbService.saveObj(book);
    }

    @RequestMapping(value = "/book/listByBook")
    public List<Book> listByBook(@RequestBody Book book) {
        return bookMongoDbService.listByBook(book);
    }

    @RequestMapping(value = "/book/list")
    public List<Book> list() {
        return bookMongoDbService.list();
    }

    @RequestMapping(value = "/book/deleteById")
    public long deleteById(String id){
        return bookMongoDbService.deleteById(id);
    }

    @RequestMapping(value = "/book/deleteByProperty")
    public long deleteByProperty(Book book){
        return bookMongoDbService.deleteByProperty(book);
    }

    public static void main(String[] args) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        String now = LocalDateTime.now().format(dateTimeFormatter);
        Book book = new Book("123",56,"红楼梦","贾宝玉和","工业出版社",
                now,now);

        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                //序列化null
                .serializeNulls()
                //格式化输出
                .setPrettyPrinting()
                //禁止转义html标签
                .disableHtmlEscaping()
                .create();
        String s = gson.toJson(book);
        System.out.println(s);

    }
}
