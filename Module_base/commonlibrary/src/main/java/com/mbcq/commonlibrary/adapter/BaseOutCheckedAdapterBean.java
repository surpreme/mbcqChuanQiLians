package com.mbcq.commonlibrary.adapter;

/**
 * 带输入框的多选
 */
public class BaseOutCheckedAdapterBean extends BaseCheckedAdapterBean {
    private String outNum="";
    private String baseOutNum="";

    public String getOutNum() {
        return outNum;
    }

    public void setOutNum(String outNum) {
        this.outNum = outNum;
    }

    public String getBaseOutNum() {
        return baseOutNum;
    }

    public void setBaseOutNum(String baseOutNum) {
        this.baseOutNum = baseOutNum;
    }
}
