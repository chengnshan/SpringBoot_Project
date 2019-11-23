package com.cxp.springboot2webmagic.task;

import com.cxp.springboot2webmagic.config.PersistencePipeline;
import com.cxp.springboot2webmagic.pojo.JobInfo;
import com.cxp.springboot2webmagic.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author : cheng
 * @date : 2019-11-21 13:45
 */
@Component
public class JobProcessor implements PageProcessor {

    @Autowired
    private PersistencePipeline persistencePipeline;

    /**抓取网站的相关配置，包括编码、抓取间隔、重试次数等*/
    private Site site = Site.me()
            //设置编码
            .setCharset("gbk")
            //设置超时时间，单位是毫秒
            .setTimeOut(10 * 1000)
            //设置重试次数
            .setRetryTimes(3)
            //设置重试的间隔时间
            .setSleepTime(1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/535.1 (KHTML, like Gecko) Chrome/14.0.835.163 Safari/535.1");

    @Override
    public void process(Page page) {
        List<Selectable> nodes = page.getHtml().css("div#resultList div.el").nodes();
        if (CollectionUtils.isEmpty(nodes)){
            this.saveJobInfo(page);
        }else {
            for (Selectable node : nodes) {
                //获取详情页面的url地址
                String jobInfourl = node.links().toString();
                //把获取到的url地址放到任务队列中
                page.addTargetRequest(jobInfourl);
            }
            //获取下一页的url
            String bkUrl = page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
            page.addTargetRequest(bkUrl);
        }
    }

    private void saveJobInfo(Page page) {
        JobInfo jobInfo = new JobInfo();

        Html html = page.getHtml();
        jobInfo.setUrl(page.getUrl().toString());
        jobInfo.setCompanyName(html.xpath("//div[@class=cn]/p/a/text()").toString());
        jobInfo.setCompanyAddr(html.xpath("//div[@class=bmsg]/p[@class=fp]/text()").toString());
        jobInfo.setCompanyInfo(html.xpath("//div[@class=tmsg][@class=inbox]/text()").toString());
        jobInfo.setJobName(html.xpath("//div[@class=cn]/h1/text()").toString());

        String title = html.css("div.cn p.ltype", "title").toString();
        html.css("div.cn p.ltype");
        if (StringUtils.isNotBlank(title)){
            arrayTrim(title.split("\\|"), jobInfo);;
        }

        jobInfo.setJobInfo(Jsoup.parse((page.getHtml().css("div.job_msg").toString())).text());

        String salary = StringUtil.conveterStr(html.css("div.cn strong", "text").toString());
        if (StringUtils.isNotBlank(salary)){
            jobInfo.setSalary(salary);
            Integer[] integers = parseSalary(salary);
            jobInfo.setSalaryMax(integers[0]);
            jobInfo.setSalaryMin(integers[1]);
        }
        page.putField("jobInfo", jobInfo);

    }

    @Override
    public Site getSite() {
        return site;
    }

    private String url = "https://search.51job.com/list/040000,000000,0000,00,9,99,java,2,1.html?lang=c&stype=&" +
            "postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0" +
            "&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";

    @Scheduled(initialDelay = 1000, fixedDelay = 100 * 1000)
    public void process(){
        //创建下载器Downloader
        HttpClientDownloader downloader = new HttpClientDownloader();
        //给下载器设置代理服务器信息
        downloader.setProxyProvider(SimpleProxyProvider.from(new Proxy("",80)));

        Spider spider = Spider.create(this).addUrl(url);
        spider.setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000000)));
        spider.addPipeline(persistencePipeline);
        spider.setDownloader(downloader);
        spider.thread(10).run();
    }

    private static void arrayTrim(String[] str, JobInfo jobInfo){
        List<String> newArr = new ArrayList<>(str.length);
        int year = LocalDate.now().getYear();
        jobInfo.setJobAddr(str[0]);
        Arrays.stream(str).forEach(arr->{
            newArr.add(StringUtil.conveterStr1(arr)) ;
            if (arr.contains("发布")){
                jobInfo.setTime(year + "-" + StringUtil.conveterStr1(arr.substring(0,arr.lastIndexOf("发布"))));
            }
        });
    }

    private static Integer[] parseSalary(String strSal){
        Integer[] salInt = new Integer[2];
        String[] split = strSal.split("\\/");
        if ("月".equals(split[1])){
            return parseUnitMonth(split[0], salInt);
        }
        if ("年".equals(split[1])){
            return parseUnitYear(split[0], salInt);
        }
        return null;
    }

    private static Integer[] parseUnitMonth(String sal,Integer[] salInt){
        if (sal.lastIndexOf("万") > 0){
            String substring = sal.substring(0, sal.length() - 1);
            String[] split = substring.split("-");
            salInt[0]= Double.valueOf(String.valueOf(Double.valueOf(split[0]) * 10000)).intValue();
            salInt[1]= (Double.valueOf(Double.valueOf(split[1]) * 10000)).intValue();
        }
        if (sal.lastIndexOf("千") > 0){
            String substring = sal.substring(0, sal.length() - 1);
            String[] split = substring.split("-");
            salInt[0]= Double.valueOf(String.valueOf(Double.valueOf(split[0]) * 1000)).intValue();
            salInt[1]= (Double.valueOf(Double.valueOf(split[1]) * 1000)).intValue();
        }
        return salInt;
    }

    private static Integer[] parseUnitYear(String sal,Integer[] salInt){
        if (sal.lastIndexOf("万") > 0){
            String substring = sal.substring(0, sal.length() - 1);
            String[] split = substring.split("-");
            salInt[0]= (Double.valueOf(Double.valueOf(split[0]) * 10000 /12 )).intValue();
            salInt[1]= (Double.valueOf(Double.valueOf(split[1]) * 10000 / 12 )).intValue();
        }
        return salInt;
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(parseSalary("1.1-2.2万/月")));
        System.out.println(Arrays.toString(parseSalary("20-30万/年")));
        System.out.println(Arrays.toString(parseSalary("4.5-6千/月")));
    }
}
