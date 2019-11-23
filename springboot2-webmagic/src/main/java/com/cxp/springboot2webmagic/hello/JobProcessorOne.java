package com.cxp.springboot2webmagic.hello;

import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : cheng
 * @date : 2019-11-21 10:03
 */
public class JobProcessorOne implements PageProcessor {

    /**抓取网站的相关配置，包括编码、抓取间隔、重试次数等*/
    private Site site = Site.me()
            //设置编码
            .setCharset("UTF-8")
            //设置超时时间，单位是毫秒
            .setTimeOut(1000)
            //设置重试次数
            .setRetryTimes(3)
            //设置重试的间隔时间
            .setSleepTime(100)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");

    /**解析页面*/
    @Override
    public void process(Page page) {
        //解析返回的数据page，并且把解析的结果放到ResultItems中

        page.putField("title",page.getHtml().xpath("//div[@class=im-header]/h2/a/text()").get());
        page.putField("链接地址",page.getHtml().xpath("//div[@class=im-header]/h2/a[@href]").get());
       /* //css选择器
        page.putField("div1",page.getHtml().css("div#maincontent p.im-subtitle").all());
        //xpath
        page.putField("div2",page.getHtml().xpath("//div[@id=maincontent]/div/div/p").all());
        //正则表达式
        page.putField("div3",page.getHtml().css("div#maincontent").regex("org.springframework.boot").all());*/
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider spider = Spider.create(new JobProcessorOne());
        List<String> urls = new ArrayList<>();
        for (int i = 1; i<=2;i++){
            urls.add("https://mvnrepository.com/search?q=org.springframework.boot&p="+i);
        }
        String[] array = urls.toArray(new String[urls.size()]);
        urls.toArray();
        spider.addUrl(array).thread(1).run();
    }
}
