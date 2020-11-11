package com.mbcq.commonlibrary.adapter;

public class BaseTextAdapterBean {

    public BaseTextAdapterBean() {

    }

    public BaseTextAdapterBean(String title, String tag) {
        this.title = title;
        this.tag = tag;
    }

    private String tag = "";
    private String title = "";

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
