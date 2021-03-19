package com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan;

public class ArrivalVehiclesScanFilterRefreshEvent {
    private int type = 0;
    private String refreshInfo = "";
    private String startDate = "";
    private String endDate = "";
    private String shippingOutletsTag = "";

    public ArrivalVehiclesScanFilterRefreshEvent(int type, String refreshInfo, String startDate, String endDate, String shippingOutletsTag) {
        this.type = type;
        this.refreshInfo = refreshInfo;
        this.startDate = startDate;
        this.endDate = endDate;
        this.shippingOutletsTag = shippingOutletsTag;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRefreshInfo() {
        return refreshInfo;
    }

    public void setRefreshInfo(String refreshInfo) {
        this.refreshInfo = refreshInfo;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getShippingOutletsTag() {
        return shippingOutletsTag;
    }

    public void setShippingOutletsTag(String shippingOutletsTag) {
        this.shippingOutletsTag = shippingOutletsTag;
    }




}
