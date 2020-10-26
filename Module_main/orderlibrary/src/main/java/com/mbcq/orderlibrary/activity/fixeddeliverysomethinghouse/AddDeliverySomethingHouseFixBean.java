package com.mbcq.orderlibrary.activity.fixeddeliverysomethinghouse;


import java.util.List;

public class AddDeliverySomethingHouseFixBean {

    /**
     * SendInOneFlag : SHSM1003-20200929-001
     * SendMan : 张凯
     * SendManTel : 16276665366
     * SendManMb : 16276665366
     * SendRemark :
     * SendVehicleNo : 浙G12364
     * SendDate : 2020-09-29 16:40:14
     */

    private String SendInOneFlag;
    private String CommonStr;
    private String SendMan;
    private String SendManTel;
    private String SendManMb;
    private String SendRemark;
    private String SendVehicleNo;
    private String SendDate;
    private String SenWebCod;
    private String SenWebCodStr;
    private List<FixedDeliverySomethingHouseBean> waybillSendDetLst;

    public List<FixedDeliverySomethingHouseBean> getWaybillSendDetLst() {
        return waybillSendDetLst;
    }

    public void setWaybillSendDetLst(List<FixedDeliverySomethingHouseBean> waybillSendDetLst) {
        this.waybillSendDetLst = waybillSendDetLst;
    }

    public String getSendInOneFlag() {
        return SendInOneFlag;
    }

    public void setSendInOneFlag(String SendInOneFlag) {
        this.SendInOneFlag = SendInOneFlag;
    }

    public String getSendMan() {
        return SendMan;
    }

    public void setSendMan(String SendMan) {
        this.SendMan = SendMan;
    }

    public String getSendManTel() {
        return SendManTel;
    }

    public void setSendManTel(String SendManTel) {
        this.SendManTel = SendManTel;
    }

    public String getSendManMb() {
        return SendManMb;
    }

    public void setSendManMb(String SendManMb) {
        this.SendManMb = SendManMb;
    }

    public String getSendRemark() {
        return SendRemark;
    }

    public void setSendRemark(String SendRemark) {
        this.SendRemark = SendRemark;
    }

    public String getSendVehicleNo() {
        return SendVehicleNo;
    }

    public void setSendVehicleNo(String SendVehicleNo) {
        this.SendVehicleNo = SendVehicleNo;
    }

    public String getSendDate() {
        return SendDate;
    }

    public void setSendDate(String SendDate) {
        this.SendDate = SendDate;
    }

    public String getSenWebCod() {
        return SenWebCod;
    }

    public void setSenWebCod(String senWebCod) {
        SenWebCod = senWebCod;
    }

    public String getSenWebCodStr() {
        return SenWebCodStr;
    }

    public void setSenWebCodStr(String senWebCodStr) {
        SenWebCodStr = senWebCodStr;
    }

    public String getCommonStr() {
        return CommonStr;
    }

    public void setCommonStr(String commonStr) {
        CommonStr = commonStr;
    }
}
