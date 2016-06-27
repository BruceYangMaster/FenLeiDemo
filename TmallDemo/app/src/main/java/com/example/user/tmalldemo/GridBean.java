package com.example.user.tmalldemo;

import java.io.Serializable;

/**
 * Created by user on 2016/5/27.
 */
public class GridBean implements Serializable{
    private String name;
    private String img;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
