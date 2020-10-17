package com.cxp.springboot2webmagic.hello;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

/**
 * @author : cheng
 * @date : 2019-11-21 10:52
 */
public class MovieByWebMagic implements PageProcessor {

    private Site site = Site.me().setRetryTimes(3).setSleepTime(1000);

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        //电影详情链接movieLink的正则表达式
        String movieLink="http://www.dytt8.net/html/gndy/\\w{4}/\\d{8}/\\d{5}.html";
        //写相应的xpath
        //电影名称
        String movieNameXpath="//div[@class='title_all']/h1/font/text()";
        //电影下载地址
        String movieDownloadXpath="//a[starts-with(@href,'ftp')]/text()";
        //电影网主界面要爬的数据所在的地址链接
        String movieLinkXpath="//div[@class='co_content4']/ul/a[@href]";

        //判断是否符合电影详情链接 movieNameXpath 的格式
        if(!page.getUrl().regex(movieLink).match()) {
            page.addTargetRequests(
                    page.getHtml().xpath(movieLinkXpath).links().all()
            );
        }
        //get()与toString()方法功能相同
        page.putField("电影名称", page.getHtml().xpath(movieNameXpath).get());
        page.putField("电影链接", page.getHtml().xpath(movieDownloadXpath).toString());
        //跳过空页
        if (page.getResultItems().get("电影名称")==null){
            //skip this page
            page.setSkip(true);
        }

    }

    public static void main(String[] args) {
        HttpClientDownloader downloader = new HttpClientDownloader();
        downloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("112.84.178.21",8888)));

        Spider.create(new MovieByWebMagic()).addUrl("https://www.dytt8.net/index0.htm")
                .run();
    }

}
