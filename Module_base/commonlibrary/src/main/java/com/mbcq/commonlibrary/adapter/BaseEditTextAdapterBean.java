package com.mbcq.commonlibrary.adapter;

public class BaseEditTextAdapterBean {
    private String title="";
    private String tag="";
    private boolean isCanInput=true;
    private String inputStr="";

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

    public String getInputStr() {
        return inputStr;
    }

    public void setInputStr(String inputStr) {
        this.inputStr = inputStr;
    }

    public boolean isCanInput() {
        return isCanInput;
    }

    public void setCanInput(boolean canInput) {
        isCanInput = canInput;
    }
}
