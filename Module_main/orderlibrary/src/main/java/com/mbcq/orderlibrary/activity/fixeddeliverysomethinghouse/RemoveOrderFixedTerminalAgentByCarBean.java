package com.mbcq.orderlibrary.activity.fixeddeliverysomethinghouse;

import java.util.List;

public class RemoveOrderFixedTerminalAgentByCarBean {

    /**
     * CommonStr : 10030000130
     * id : 6
     * sendInOneFlag : SHSM1003-20201009-001
     */

    private String CommonStr;
    private String id;
    private String sendInOneFlag;
    private List<FixedDeliverySomethingHouseBean> WaybillSendDetLst;

    public String getCommonStr() {
        return CommonStr;
    }

    public void setCommonStr(String CommonStr) {
        this.CommonStr = CommonStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public List<FixedDeliverySomethingHouseBean> getWaybillSendDetLst() {
        return WaybillSendDetLst;
    }

    public void setWaybillSendDetLst(List<FixedDeliverySomethingHouseBean> waybillSendDetLst) {
        WaybillSendDetLst = waybillSendDetLst;
    }

    public String getSendInOneFlag() {
        return sendInOneFlag;
    }

    public void setSendInOneFlag(String sendInOneFlag) {
        this.sendInOneFlag = sendInOneFlag;
    }
}
