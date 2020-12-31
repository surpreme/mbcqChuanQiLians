package com.mbcq.orderlibrary.fragment.waybillscan;

public class WaybillScanFragmentListBean {

    /**
     * id : 10708
     * billno : 10030004857
     * lableNo : 100300048570001
     * webidCode : 1003
     * webidCodeStr : 汕头
     * scanOpeType : 0
     * scanOpeTypeStr : 短驳装车
     * deviceNo : 92:FD:7D:F0:1A:6D
     * content : PDA货物从汕头出发，下一站是，操作员是lzy
     * inOneVehicleFlag : DB1003-20201211-003
     * scanType : 0
     * scanTypeStr : PDA
     * opeMan : lzy
     * recordDate : 2020-12-11T11:12:03
     */

    private int id;
    private String billno;
    private String lableNo;
    private int webidCode;
    private String webidCodeStr;
    private int scanOpeType;
    private String scanOpeTypeStr;
    private String deviceNo;
    private String content;
    private String inOneVehicleFlag;
    private int scanType;
    private String scanTypeStr;
    private String opeMan;
    private String recordDate;

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

    public String getLableNo() {
        return lableNo;
    }

    public void setLableNo(String lableNo) {
        this.lableNo = lableNo;
    }

    public int getWebidCode() {
        return webidCode;
    }

    public void setWebidCode(int webidCode) {
        this.webidCode = webidCode;
    }

    public String getWebidCodeStr() {
        return webidCodeStr;
    }

    public void setWebidCodeStr(String webidCodeStr) {
        this.webidCodeStr = webidCodeStr;
    }

    public int getScanOpeType() {
        return scanOpeType;
    }

    public void setScanOpeType(int scanOpeType) {
        this.scanOpeType = scanOpeType;
    }

    public String getScanOpeTypeStr() {
        return scanOpeTypeStr;
    }

    public void setScanOpeTypeStr(String scanOpeTypeStr) {
        this.scanOpeTypeStr = scanOpeTypeStr;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getInOneVehicleFlag() {
        return inOneVehicleFlag;
    }

    public void setInOneVehicleFlag(String inOneVehicleFlag) {
        this.inOneVehicleFlag = inOneVehicleFlag;
    }

    public int getScanType() {
        return scanType;
    }

    public void setScanType(int scanType) {
        this.scanType = scanType;
    }

    public String getScanTypeStr() {
        return scanTypeStr;
    }

    public void setScanTypeStr(String scanTypeStr) {
        this.scanTypeStr = scanTypeStr;
    }

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
}
