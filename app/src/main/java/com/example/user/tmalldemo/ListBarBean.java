package com.example.user.tmalldemo;

import java.io.Serializable;

/**
 * Created by user on 2016/5/30.
 */
public class ListBarBean implements Serializable {
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
