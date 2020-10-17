package com.cxp;

import static org.junit.Assert.assertTrue;

import com.cxp.pojo.Book;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

import java.util.function.Consumer;

/**
 * Unit test for simple App.
 */
public class MongodbBasicAppTest
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
        try{
            // 连接到 mongodb 服务
            MongoClient mongoClient = new MongoClient( "192.168.8.113" , 27017 );

            // 连接到数据库
            MongoDatabase mongoDatabase = mongoClient.getDatabase("chengxp");
            System.out.println("Connect to database successfully");

        }catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
    }
}
