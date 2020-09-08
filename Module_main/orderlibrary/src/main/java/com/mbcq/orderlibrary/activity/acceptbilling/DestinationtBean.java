package com.mbcq.orderlibrary.activity.acceptbilling;

import com.mbcq.commonlibrary.adapter.BaseAdapterBean;

public class DestinationtBean   {

    /**
     * id : 50
     * companyId : 2001
     * belWebCod : 1003
     * belWebCodStr : 汕头
     * ewebidCode : 1001
     * ewebidCodeStr : 义乌后湖
     * mapDes : 义乌后湖3
     * opeMan : 汕头
     * recordDate : 2020-06-05T09:52:06
     */

    private int id;
    private int companyId;
    private int belWebCod;
    private String belWebCodStr;
    private int ewebidCode;
    private String ewebidCodeStr;
    private String mapDes;
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

    public int getBelWebCod() {
        return belWebCod;
    }

    public void setBelWebCod(int belWebCod) {
        this.belWebCod = belWebCod;
    }

    public String getBelWebCodStr() {
        return belWebCodStr;
    }

    public void setBelWebCodStr(String belWebCodStr) {
        this.belWebCodStr = belWebCodStr;
    }

    public int getEwebidCode() {
        return ewebidCode;
    }

    public void setEwebidCode(int ewebidCode) {
        this.ewebidCode = ewebidCode;
    }

    public String getEwebidCodeStr() {
        return ewebidCodeStr;
    }

    public void setEwebidCodeStr(String ewebidCodeStr) {
        this.ewebidCodeStr = ewebidCodeStr;
    }

    public String getMapDes() {
        return mapDes;
    }

    public void setMapDes(String mapDes) {
        this.mapDes = mapDes;
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
