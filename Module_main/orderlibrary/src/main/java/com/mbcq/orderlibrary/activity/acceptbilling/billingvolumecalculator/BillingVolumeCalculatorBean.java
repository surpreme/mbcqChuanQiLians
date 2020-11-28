package com.mbcq.orderlibrary.activity.acceptbilling.billingvolumecalculator;

public class BillingVolumeCalculatorBean {
    //宽
    private String singleWidth = "00.00";
    //长
    private String singleLong = "00.00";
    //高
    private String singleHeight = "00.00";
    private String number = "0";
    private String totalWeight = "00.00";

    public BillingVolumeCalculatorBean(String singleWidth, String singleLong, String singleHeight, String number, String totalWeight) {
        this.singleWidth = singleWidth;
        this.singleLong = singleLong;
        this.singleHeight = singleHeight;
        this.number = number;
        this.totalWeight = totalWeight;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getTotalWeight() {
        return totalWeight;
    }

    public void setTotalWeight(String totalWeight) {
        this.totalWeight = totalWeight;
    }


    public String getSingleHeight() {
        return singleHeight;
    }

    public void setSingleHeight(String singleHeight) {
        this.singleHeight = singleHeight;
    }

    public String getSingleWidth() {
        return singleWidth;
    }

    public void setSingleWidth(String singleWidth) {
        this.singleWidth = singleWidth;
    }

    public String getSingleLong() {
        return singleLong;
    }

    public void setSingleLong(String singleLong) {
        this.singleLong = singleLong;
    }
}
