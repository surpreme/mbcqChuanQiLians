package com.mbcq.vehicleslibrary.activity.alldeparturerecord.departurerecord;

public class DepartureRecordAddSuccessEvent {
    public DepartureRecordAddSuccessEvent(int refreshType) {
        this.refreshType = refreshType;
    }

    private int refreshType;

    public int getRefreshType() {
        return refreshType;
    }

    public void setRefreshType(int refreshType) {
        this.refreshType = refreshType;
    }
}
