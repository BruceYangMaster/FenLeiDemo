package com.example.user.tmalldemo;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by user on 2016/5/27.
 */
public class ListBean implements Serializable{
    private String text;
    private ArrayList<GridBean> data;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ArrayList<GridBean> getData() {
        return data;
    }

    public void setData(ArrayList<GridBean> data) {
        this.data = data;
    }
}
