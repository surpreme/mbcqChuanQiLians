package com.mbcq.commonlibrary.adapter;

/**
 * 带输入框的多选
 */
public class BaseOutCheckedAdapterBean extends BaseCheckedAdapterBean {
    private String outNum="";

    public String getOutNum() {
        return outNum;
    }

    public void setOutNum(String outNum) {
        this.outNum = outNum;
    }
}
