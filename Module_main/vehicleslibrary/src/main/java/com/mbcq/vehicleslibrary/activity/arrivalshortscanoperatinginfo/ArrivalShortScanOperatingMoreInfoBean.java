package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperatinginfo;

public class ArrivalShortScanOperatingMoreInfoBean {
    /**
     * billno : 10010000138
     * lableNo : 100100001380024
     * inOneVehicleFlag : GX1001-20210316-008
     * shipper : 宋学宝
     * consignee : 非常慢
     * webidCode : 1001
     * webidCodeStr : 义乌后湖
     * ewebidCode : 1003
     * ewebidCodeStr : 汕头
     * scanTypeStr : PDA
     * scanType:0
     * xcScanPercentage：75.00
     */

    private String billno="";
    private String lableNo="";
    private String inOneVehicleFlag="";
    private String opeMan="";
    private String recordDate="";
    private String shipper="";
    private String consignee="";
    private String webidCode="";
    private String webidCodeStr="";
    private String ewebidCode="";
    private String ewebidCodeStr="";
    private String scanTypeStr="";
    private String mDismantleInfo="";
    private int scanType;
    private boolean isScan;

    public String getOpeMan() {
        return opeMan;
    }

    public void setOpeMan(String opeMan) {
        this.opeMan = opeMan;
    }

    public String getRecordDate() {
        return recordDate;
    }

    public void setRecordDate(String recordDate) {
        this.recordDate = recordDate;
    }
    public String getScanTypeStr() {
        return scanTypeStr;
    }

    public void setScanTypeStr(String scanTypeStr) {
        this.scanTypeStr = scanTypeStr;
    }

    public int getScanType() {
        return scanType;
    }

    public void setScanType(int scanType) {
        this.scanType = scanType;
    }


    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public String getLableNo() {
        return lableNo;
    }

    public void setLableNo(String lableNo) {
        this.lableNo = lableNo;
    }

    public String getInOneVehicleFlag() {
        return inOneVehicleFlag;
    }

    public void setInOneVehicleFlag(String inOneVehicleFlag) {
        this.inOneVehicleFlag = inOneVehicleFlag;
    }

    public String getShipper() {
        return shipper;
    }

    public void setShipper(String shipper) {
        this.shipper = shipper;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
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

    public boolean isScan() {
        return isScan;
    }

    public void setScan(boolean scan) {
        isScan = scan;
    }

    public String getmDismantleInfo() {
        return mDismantleInfo;
    }

    public void setmDismantleInfo(String mDismantleInfo) {
        this.mDismantleInfo = mDismantleInfo;
    }
}
