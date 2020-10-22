package com.mbcq.accountlibrary.fragment.setting.bluetooth;

public class BlueToothDeviceListBean {
    private String deviceName="";
    private String deviceHardwareAddress="";

    public String getDeviceHardwareAddress() {
        return deviceHardwareAddress;
    }

    public void setDeviceHardwareAddress(String deviceHardwareAddress) {
        this.deviceHardwareAddress = deviceHardwareAddress;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }
}
