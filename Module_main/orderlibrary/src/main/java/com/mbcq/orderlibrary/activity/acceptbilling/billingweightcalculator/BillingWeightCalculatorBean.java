package com.mbcq.orderlibrary.activity.acceptbilling.billingweightcalculator;

public class BillingWeightCalculatorBean {
    private String singleWeight="00.00";
    private String number="0";
    private String totalWeight="00.00";
    public String getSingleWeight() {
        return singleWeight;
    }

    public BillingWeightCalculatorBean(String singleWeight, String number, String totalWeight) {
        this.singleWeight = singleWeight;
        this.number = number;
        this.totalWeight = totalWeight;
    }

    public void setSingleWeight(String singleWeight) {
        this.singleWeight = singleWeight;
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


}
