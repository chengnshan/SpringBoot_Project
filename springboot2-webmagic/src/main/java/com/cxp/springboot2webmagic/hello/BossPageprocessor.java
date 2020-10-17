package com.cxp.springboot2webmagic.hello;

import org.jsoup.Jsoup;
import org.springframework.util.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @author : cheng
 * @date : 2019-11-26 19:27
 */
public class BossPageprocessor implements PageProcessor {

    private Site site = Site.me()
            //设置超时时间，单位是毫秒
            .setTimeOut(10 * 1000)
    //设置重试次数
            .setRetryTimes(3)
    //设置重试的间隔时间
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");


    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().css("div.info-primary h3").nodes();
        String detailUrl = null;
        if (CollectionUtils.isEmpty(nodes)){
            content(page);
        }else {
            for (Selectable node : nodes) {
                detailUrl = node.links().get();

                page.addTargetRequest(detailUrl);
            }
//            detailUrl = page.getHtml().css("div.job-list div.page a.next").links().toString();
//            page.addTargetRequest(detailUrl);
        }

    }

    private void content(Page page){
        page.putField("jobDescription", Jsoup.parse(page.getHtml().css("div.detail-content div.job-sec div.text").toString()).text());
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        String url = "https://www.zhipin.com/job_detail/0daef9210b2f3a090nB92tq9F1A~.html?ka=search_list_1";

     //   HttpClientDownloader downloader = new HttpClientDownloader();
     //   downloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("112.84.178.21",8888)));
        Spider.create(new BossPageprocessor())
                .addUrl(url)
         //       .setDownloader(downloader)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000) ))
                .thread(1)
                .run();
    }
}
