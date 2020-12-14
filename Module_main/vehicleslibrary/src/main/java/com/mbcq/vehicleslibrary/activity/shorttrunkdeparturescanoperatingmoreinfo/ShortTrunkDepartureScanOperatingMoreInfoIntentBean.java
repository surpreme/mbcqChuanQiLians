package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperatingmoreinfo;

import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.ShortTrunkDepartureScanOperatingBean;

import java.util.List;

public class ShortTrunkDepartureScanOperatingMoreInfoIntentBean extends ShortTrunkDepartureScanOperatingBean {
    private String inOneVehicleFlag="";
    private int goodsTotalNum=0;
    private int mType=0;

    public String getInOneVehicleFlag() {
        return inOneVehicleFlag;
    }

    public void setInOneVehicleFlag(String inOneVehicleFlag) {
        this.inOneVehicleFlag = inOneVehicleFlag;
    }



    public int getGoodsTotalNum() {
        return goodsTotalNum;
    }

    public void setGoodsTotalNum(int goodsTotalNum) {
        this.goodsTotalNum = goodsTotalNum;
    }



    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }


}
