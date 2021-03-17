package com.mbcq.vehicleslibrary.fragment;

public class ArrivalVehiclesEvent {
    public ArrivalVehiclesEvent(int type, String info) {
        this.type = type;
        this.info = info;
    }

    private int type=-1;
    private String info="";

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
