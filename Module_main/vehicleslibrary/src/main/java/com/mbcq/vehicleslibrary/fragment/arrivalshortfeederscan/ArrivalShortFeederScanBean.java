package com.mbcq.vehicleslibrary.fragment.arrivalshortfeederscan;

public class ArrivalShortFeederScanBean {

    /**
     * inoneVehicleFlag : DB1001-20210319-003
     * sendDate : 2021-03-19 15:08:22
     * vehicleState : 1
     * vehicleStateStr : 发货
     * webidCode : 1001
     * webidCodeStr : 义乌后湖
     * ewebidCode : 1003
     * ewebidCodeStr : 汕头
     * vehicleNo : 浙A90832
     * chauffer : 张瑞丽
     * chaufferMb : 13422234441
     * ticketNum : 2
     * qtyNum : 4.0
     * volumnNum : 2.0
     * weightNum : 2.0
     * accTansSum : 0.0
     * xcScanPercentage : null
     */

    private String inoneVehicleFlag;
    private String sendDate;
    private int vehicleState;
    private String vehicleStateStr;
    private String webidCode;
    private String webidCodeStr;
    private String ewebidCode;
    private String ewebidCodeStr;
    private String vehicleNo;
    private String chauffer;
    private String chaufferMb;
    private String ticketNum;
    private String qtyNum;
    private String volumnNum;
    private String weightNum;
    private String accTansSum;
    private boolean isChecked=false;
    private String xcScanPercentage="0";
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

    public int getVehicleState() {
        return vehicleState;
    }

    public void setVehicleState(int vehicleState) {
        this.vehicleState = vehicleState;
    }

    public String getVehicleStateStr() {
        return vehicleStateStr;
    }

    public void setVehicleStateStr(String vehicleStateStr) {
        this.vehicleStateStr = vehicleStateStr;
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

    public String getChaufferMb() {
        return chaufferMb;
    }

    public void setChaufferMb(String chaufferMb) {
        this.chaufferMb = chaufferMb;
    }

    public String getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(String ticketNum) {
        this.ticketNum = ticketNum;
    }

    public String getQtyNum() {
        return qtyNum;
    }

    public void setQtyNum(String qtyNum) {
        this.qtyNum = qtyNum;
    }

    public String getVolumnNum() {
        return volumnNum;
    }

    public void setVolumnNum(String volumnNum) {
        this.volumnNum = volumnNum;
    }

    public String getWeightNum() {
        return weightNum;
    }

    public void setWeightNum(String weightNum) {
        this.weightNum = weightNum;
    }

    public String getAccTansSum() {
        return accTansSum;
    }

    public void setAccTansSum(String accTansSum) {
        this.accTansSum = accTansSum;
    }

    public String getXcScanPercentage() {
        return xcScanPercentage;
    }

    public void setXcScanPercentage(String xcScanPercentage) {
        this.xcScanPercentage = xcScanPercentage;
    }
}
