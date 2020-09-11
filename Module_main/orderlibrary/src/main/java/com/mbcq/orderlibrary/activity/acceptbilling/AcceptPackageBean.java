package com.mbcq.orderlibrary.activity.acceptbilling;

import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean;

public class AcceptPackageBean extends BaseTextAdapterBean {


    /**
     * id : 33
     * companyId : 2001
     * webidCode : 0
     * webidCodeStr :
     * packages : 工工式
     * opeMan : 汕头
     * recordDate : 2020-04-03T09:47:55
     */

    private int id;
    private int companyId;
    private int webidCode;
    private String webidCodeStr;
    private String packages;
    private String opeMan;
    private String recordDate;

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

    public String getPackages() {
        return packages;
    }

    public void setPackages(String packages) {
        this.packages = packages;
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
