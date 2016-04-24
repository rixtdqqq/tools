package com.example.administrator.myapplication.entites;

/**
 * MusicInfo 音乐信息类
 * Created by zhuyingxin at 2016/2/26 21:16.
 * QQ: 657036139
 */
public class MusicInfo {
    private String fname;
    private String fpath;
    private String duration;

    public String getFname() {
        return fname;
    }

    public String getFpath() {
        return fpath;
    }

    public String getDuration() {
        return duration;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public void setFpath(String fpath) {
        this.fpath = fpath;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}
