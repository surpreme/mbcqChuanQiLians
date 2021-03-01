package com.mbcq.orderlibrary.activity.acceptbilling;

public class AddGoodsAcceptBillingBean {
    private String product="";//货物名称
    private String qty="";//件数
    private String packages="";//包装方式
    private String weight="";//重量
    private String volumn="";//体积
    private String qtyPrice="";//数量单价
    private String wPrice="";//重量单价
    private String vPrice="";//体积单价
    private String safeMoney="";//保价金额
    private String lightandheavy="";//轻重货
    private String weightJs="";//结算重量

    public String getLightandheavy() {
        return lightandheavy;
    }

    public void setLightandheavy(String lightandheavy) {
        this.lightandheavy = lightandheavy;
    }

    public String getWeightJs() {
        return weightJs;
    }

    public void setWeightJs(String weightJs) {
        this.weightJs = weightJs;
    }


    public String getQtyPrice() {
        return qtyPrice;
    }

    public void setQtyPrice(String qtyPrice) {
        this.qtyPrice = qtyPrice;
    }

    public String getwPrice() {
        return wPrice;
    }

    public void setwPrice(String wPrice) {
        this.wPrice = wPrice;
    }

    public String getvPrice() {
        return vPrice;
    }

    public void setvPrice(String vPrice) {
        this.vPrice = vPrice;
    }

    public String getSafeMoney() {
        return safeMoney;
    }

    public void setSafeMoney(String safeMoney) {
        this.safeMoney = safeMoney;
    }


    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQty() {
        return qty;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getVolumn() {
        return volumn;
    }

    public void setVolumn(String volumn) {
        this.volumn = volumn;
    }
}
