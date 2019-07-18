package com.cxp.springbootelasticsearch;

import com.cxp.springbootelasticsearch.utils.ElasticsearchUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootElasticsearchApplicationTests {

    @Autowired
    private TransportClient client;

    @RequestMapping("/testElastic")
    public Map testElastic(String pname) {
        System.out.println(ElasticsearchUtils.isIndexExist("index1", client));
        System.out.println(ElasticsearchUtils.isIndexExist("index1"));
        Map<String, Object> stringObjectMap =
                ElasticsearchUtils.searchDataById("my-index", "persion", "1", null);
        System.out.println(stringObjectMap);
        return stringObjectMap;
    }

    @Test
    public void contextLoads() {
        ElasticsearchUtils.createIndex("index1");
        System.out.println(ElasticsearchUtils.isIndexExist("index1", client));
        System.out.println(ElasticsearchUtils.isIndexExist("index1"));

    }

}
