package org.luoyifeng.wechatLife.test;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.processor.example.GithubRepoPageProcessor;

/**
 * @Discribe
 * @Auther 罗一峰[572922338@qq.com]
 * @Date 2017.12.14 21:55
 */
public class PaChongTest1 implements PageProcessor {
    // 部分一：抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    //重试次数为3次，抓取间隔为一秒
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    /**
     * process是定制爬虫逻辑的核心接口，在这里编写抽取逻辑
     * @param page
     */
    public void process(Page page) {
        // 部分二：定义如何抽取页面信息，并保存下来
        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());
        page.putField("name", page.getHtml().xpath("//h1[@class='entry-title public']/strong/a/text()").toString());
        if (page.getResultItems().get("name")==null){
            // skip this page
            page.setSkip(true);
        }
        page.putField("readme", page.getHtml().xpath("//div[@id='readme']/tidyText()"));

        // 部分三：从页面发现后续的url地址来抓取
        // 用到了正则表达式，它表示匹配所有"https://github.com/code4craft/webmagic"这样的链接。
        // page.addTargetRequests()则将这些链接加入到待抓取的队列中去。
        page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/[\\w\\-]+/[\\w\\-]+)").all());
    }

    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new GithubRepoPageProcessor())
                //从"https://github.com/code4craft"开始抓
                .addUrl("https://github.com/code4craft")
                //开启5个线程抓取
                .thread(5)
                //启动爬虫
                .run();
    }
}
