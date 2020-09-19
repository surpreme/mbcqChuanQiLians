package com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixtrunkdeparturehouse;

import com.mbcq.vehicleslibrary.bean.StockWaybillListBean;

import java.util.List;

public class AddTrunkOrderDataBean {
    private String commonStr;
    private String id;
    private String inoneVehicleFlag;
    private List<StockWaybillListBean> gxVehicleDetLst;

    public String getCommonStr() {
        return commonStr;
    }

    public void setCommonStr(String commonStr) {
        this.commonStr = commonStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInoneVehicleFlag() {
        return inoneVehicleFlag;
    }

    public void setInoneVehicleFlag(String inoneVehicleFlag) {
        this.inoneVehicleFlag = inoneVehicleFlag;
    }


    public List<StockWaybillListBean> getGxVehicleDetLst() {
        return gxVehicleDetLst;
    }

    public void setGxVehicleDetLst(List<StockWaybillListBean> gxVehicleDetLst) {
        this.gxVehicleDetLst = gxVehicleDetLst;
    }
}
