package com.mbcq.commonlibrary;

public class LocationResultEvent {
    private String resultStr = "";
    private int type = 0;
    private String latitude = "";
    private String longitude = "";

    public LocationResultEvent(String resultStr, int type, String latitude, String longitude) {
        this.resultStr = resultStr;
        this.type = type;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getResultStr() {
        return resultStr;
    }

    public void setResultStr(String resultStr) {
        this.resultStr = resultStr;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }


}
