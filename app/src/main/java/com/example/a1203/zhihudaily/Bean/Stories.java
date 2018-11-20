package com.example.a1203.zhihudaily.Bean;

import java.util.List;

/**
 * @author littlecorgi
 * @Date 2018-11-04 15:09
 * @email a1203991686@126.com
 */
public class Stories {

    private int type;

    private int id;

    private String ga_prefix;

    private String title;

    private List<String> images;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
