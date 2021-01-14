package com.mbcq.amountlibrary.activity.allwriteoffreceivepayment.commonreceivewriteoff;

public class CommonReceiveWriteOffBean {

    /**
     * inoneVehicleFlag : DB1003-20210114-001
     * sendDate : 2021-01-14 08:50:40
     * contractNo : DB1003-20210114-001
     * vehicleNo : 浙G12370
     * chauffer : 张凯
     * webidCode : 1003
     * webidCodeStr : 汕头
     * ewebidCode : 1001
     * ewebidCodeStr : 义乌后湖
     * xmtype : 到付上转车费
     * accNow : 9
     * companyId : 2001
     * hxtype : 未收款
     * receiptDate : null
     * accWebidCode : null
     * accWebidCodeStr : null
     * billno : null
     * isChecked : false
     */

    private String inoneVehicleFlag;
    private String sendDate;
    private String contractNo;
    private String vehicleNo;
    private String chauffer;
    private String webidCode;
    private String webidCodeStr;
    private String ewebidCode;
    private String ewebidCodeStr;
    private String xmtype;
    private String accNow;
    private String companyId;
    private String hxtype;
    private String receiptDate;
    private String accWebidCode;
    private String accWebidCodeStr;
    private String billno;
    private boolean isChecked=false;
    private String mCommonTitleStr="";

    public String getmCommonTitleStr() {
        return mCommonTitleStr;
    }

    public void setmCommonTitleStr(String mCommonTitleStr) {
        this.mCommonTitleStr = mCommonTitleStr;
    }


    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }


    public String getInoneVehicleFlag() {
        return inoneVehicleFlag;
    }

    public void setInoneVehicleFlag(String inoneVehicleFlag) {
        this.inoneVehicleFlag = inoneVehicleFlag;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getChauffer() {
        return chauffer;
    }

    public void setChauffer(String chauffer) {
        this.chauffer = chauffer;
    }

    public String getWebidCode() {
        return webidCode;
    }

    public void setWebidCode(String webidCode) {
        this.webidCode = webidCode;
    }

    public String getWebidCodeStr() {
        return webidCodeStr;
    }

    public void setWebidCodeStr(String webidCodeStr) {
        this.webidCodeStr = webidCodeStr;
    }

    public String getEwebidCode() {
        return ewebidCode;
    }

    public void setEwebidCode(String ewebidCode) {
        this.ewebidCode = ewebidCode;
    }

    public String getEwebidCodeStr() {
        return ewebidCodeStr;
    }

    public void setEwebidCodeStr(String ewebidCodeStr) {
        this.ewebidCodeStr = ewebidCodeStr;
    }

    public String getXmtype() {
        return xmtype;
    }

    public void setXmtype(String xmtype) {
        this.xmtype = xmtype;
    }

    public String getAccNow() {
        return accNow;
    }

    public void setAccNow(String accNow) {
        this.accNow = accNow;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getHxtype() {
        return hxtype;
    }

    public void setHxtype(String hxtype) {
        this.hxtype = hxtype;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getAccWebidCode() {
        return accWebidCode;
    }

    public void setAccWebidCode(String accWebidCode) {
        this.accWebidCode = accWebidCode;
    }

    public String getAccWebidCodeStr() {
        return accWebidCodeStr;
    }

    public void setAccWebidCodeStr(String accWebidCodeStr) {
        this.accWebidCodeStr = accWebidCodeStr;
    }

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

}
