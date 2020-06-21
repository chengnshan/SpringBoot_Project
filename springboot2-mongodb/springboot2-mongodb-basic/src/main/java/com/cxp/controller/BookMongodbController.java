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

import java.util.Calendar;
import java.util.List;

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

    @RequestMapping(value = "/book/list")
    public List<Book> list(@RequestBody Book book) {
        return bookMongoDbService.list(book);
    }


    public static void main(String[] args) {
        Book book = new Book("123",56,"红楼梦","贾宝玉和","工业出版社",
                Calendar.getInstance().getTime(),Calendar.getInstance().getTime());

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
