package com.mbcq.orderlibrary.activity.receipt.receiptcancel;

public class ReceiptCancelBean {

    /**
     * cztype : 签收
     * gsid : 2001
     * czgs : test2
     * czwebid :
     * czdate : 2020-11-07T00:00:00
     * czman : 汕头
     * id : 29
     * billno : 10010000019
     */

    private String cztype;
    private int gsid;
    private String czgs;
    private String czwebid;
    private String czdate;
    private String czman;
    private int id;
    private String billno;

    public String getCztype() {
        return cztype;
    }

    public void setCztype(String cztype) {
        this.cztype = cztype;
    }

    public int getGsid() {
        return gsid;
    }

    public void setGsid(int gsid) {
        this.gsid = gsid;
    }

    public String getCzgs() {
        return czgs;
    }

    public void setCzgs(String czgs) {
        this.czgs = czgs;
    }

    public String getCzwebid() {
        return czwebid;
    }

    public void setCzwebid(String czwebid) {
        this.czwebid = czwebid;
    }

    public String getCzdate() {
        return czdate;
    }

    public void setCzdate(String czdate) {
        this.czdate = czdate;
    }

    public String getCzman() {
        return czman;
    }

    public void setCzman(String czman) {
        this.czman = czman;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }
}
