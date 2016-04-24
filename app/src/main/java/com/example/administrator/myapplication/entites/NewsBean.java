package com.example.administrator.myapplication.entites;

/**
 * NewsBean
 * Created by zhuyingxin at 2016/1/14 19:58.
 * QQ: 657036139
 */
public class NewsBean {
    /**图片地址*/
    private String newsIconUrl;
    /**新闻标题*/
    private String title;
    /**新闻简介*/
    private String content;

    public NewsBean(String newsIconUrl, String title, String content) {
        this.newsIconUrl = newsIconUrl;
        this.title = title;
        this.content = content;
    }

    public NewsBean() {
    }

    public String getNewsIconUrl() {
        return newsIconUrl;
    }

    public void setNewsIconUrl(String newsIconUrl) {
        this.newsIconUrl = newsIconUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "NewsBean{" +
                "newsIconUrl='" + newsIconUrl + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }


}
