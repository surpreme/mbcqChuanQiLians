package com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalrecord;

public class ArrivalRecordRefreshEvent {
    public ArrivalRecordRefreshEvent(String num, int type) {
        this.num = num;
        this.type = type;
    }

    private String num="";
    private int type=0;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
