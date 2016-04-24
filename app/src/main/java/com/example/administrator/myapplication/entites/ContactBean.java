package com.example.administrator.myapplication.entites;

/**
 * ContactBean
 * Created by zhuyingxin at 2016/1/29 1:28.
 * QQ: 657036139
 */
public class ContactBean {
    private String name;
    private char alpha;

    public ContactBean(String name, char alpha) {
        this.name = name;
        this.alpha = alpha;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public char getAlpha() {
        return alpha;
    }

}
