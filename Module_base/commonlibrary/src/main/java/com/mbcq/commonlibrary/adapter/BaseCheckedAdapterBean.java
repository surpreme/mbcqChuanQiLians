package com.mbcq.commonlibrary.adapter;

public class BaseCheckedAdapterBean {
    private String title="";
    private String tag="";
    private Boolean isChecked=false;

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

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }
}
