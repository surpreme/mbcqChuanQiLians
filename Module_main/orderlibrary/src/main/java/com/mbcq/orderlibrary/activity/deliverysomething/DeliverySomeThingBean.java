package com.mbcq.orderlibrary.activity.deliverysomething;

public class DeliverySomeThingBean {

    /**
     * id : 3
     * companyId : 2001
     * senWebCod : 1001
     * senWebCodStr : 义乌后湖
     * sendInOneFlag : SHSM1001-20200622-001
     * sendVehicleNo :
     * sendMan :
     * sendManTel :
     * sendManMb :
     * sendDate : 2020-06-22T08:52:15
     * sendRemark :
     * sendTimes : 0
     * opeMan : 义乌后湖
     */

    private int id;
    private int companyId;
    private int senWebCod;
    private String senWebCodStr;
    private String sendInOneFlag;
    private String sendVehicleNo;
    private String sendMan;
    private String sendManTel;
    private String sendManMb;
    private String sendDate;
    private String sendRemark;
    private int sendTimes;
    private String opeMan;
    private boolean isChecked=false;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getSenWebCod() {
        return senWebCod;
    }

    public void setSenWebCod(int senWebCod) {
        this.senWebCod = senWebCod;
    }

    public String getSenWebCodStr() {
        return senWebCodStr;
    }

    public void setSenWebCodStr(String senWebCodStr) {
        this.senWebCodStr = senWebCodStr;
    }

    public String getSendInOneFlag() {
        return sendInOneFlag;
    }

    public void setSendInOneFlag(String sendInOneFlag) {
        this.sendInOneFlag = sendInOneFlag;
    }

    public String getSendVehicleNo() {
        return sendVehicleNo;
    }

    public void setSendVehicleNo(String sendVehicleNo) {
        this.sendVehicleNo = sendVehicleNo;
    }

    public String getSendMan() {
        return sendMan;
    }

    public void setSendMan(String sendMan) {
        this.sendMan = sendMan;
    }

    public String getSendManTel() {
        return sendManTel;
    }

    public void setSendManTel(String sendManTel) {
        this.sendManTel = sendManTel;
    }

    public String getSendManMb() {
        return sendManMb;
    }

    public void setSendManMb(String sendManMb) {
        this.sendManMb = sendManMb;
    }

    public String getSendDate() {
        return sendDate;
    }

    public void setSendDate(String sendDate) {
        this.sendDate = sendDate;
    }

    public String getSendRemark() {
        return sendRemark;
    }

    public void setSendRemark(String sendRemark) {
        this.sendRemark = sendRemark;
    }

    public int getSendTimes() {
        return sendTimes;
    }

    public void setSendTimes(int sendTimes) {
        this.sendTimes = sendTimes;
    }

    public String getOpeMan() {
        return opeMan;
    }

    public void setOpeMan(String opeMan) {
        this.opeMan = opeMan;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
