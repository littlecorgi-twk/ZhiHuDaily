package com.example.a1203.zhihudaily.bean;

import java.util.List;

/**
 * @author littlecorgi
 * @Date 2018-11-07 20:28
 * @email a1203991686@126.com
 */
public class ArticleBefore {

    private String date;

    private List<Stories> stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<Stories> getStories() {
        return stories;
    }

    public void setStories(List<Stories> stories) {
        this.stories = stories;
    }

}
