package com.mbcq.vehicleslibrary.activity.stowagealongwayhouse;

import com.mbcq.vehicleslibrary.bean.StockWaybillListBean;

import java.util.List;

public class SaveStowageAlongWayHouseBean {

    /**
     * ewebidCodeStr : 汕头
     * inoneVehicleFlag : GX1003-20200628-001
     * GxVehicleDetLst : []
     * CommonStr:
     */

    private String ewebidCodeStr;
    private String inoneVehicleFlag;
    private String commonStr;
    private List<StockWaybillListBean> GxVehicleDetLst;

    public String getEwebidCodeStr() {
        return ewebidCodeStr;
    }

    public void setEwebidCodeStr(String ewebidCodeStr) {
        this.ewebidCodeStr = ewebidCodeStr;
    }

    public String getInoneVehicleFlag() {
        return inoneVehicleFlag;
    }

    public void setInoneVehicleFlag(String inoneVehicleFlag) {
        this.inoneVehicleFlag = inoneVehicleFlag;
    }

    public List<StockWaybillListBean> getGxVehicleDetLst() {
        return GxVehicleDetLst;
    }

    public void setGxVehicleDetLst(List<StockWaybillListBean> GxVehicleDetLst) {
        this.GxVehicleDetLst = GxVehicleDetLst;
    }

    public String getCommonStr() {
        return commonStr;
    }

    public void setCommonStr(String commonStr) {
        this.commonStr = commonStr;
    }
}
