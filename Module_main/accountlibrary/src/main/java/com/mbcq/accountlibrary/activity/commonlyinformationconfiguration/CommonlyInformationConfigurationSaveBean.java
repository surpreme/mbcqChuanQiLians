package com.mbcq.accountlibrary.activity.commonlyinformationconfiguration;

import java.util.List;

public class CommonlyInformationConfigurationSaveBean {
    private List<CommonlyInformationConfigurationSaveItemBean> list;

    public CommonlyInformationConfigurationSaveBean(List<CommonlyInformationConfigurationSaveItemBean> list) {
        this.list = list;
    }

    public CommonlyInformationConfigurationSaveBean() {
    }

    public List<CommonlyInformationConfigurationSaveItemBean> getList() {
        return list;
    }

    public void setList(List<CommonlyInformationConfigurationSaveItemBean> list) {
        this.list = list;
    }


    public static class CommonlyInformationConfigurationSaveItemBean {
        private String mTitle = "";
        private String mContentStr = "";

        public String getmTitle() {
            return mTitle;
        }

        public void setmTitle(String mTitle) {
            this.mTitle = mTitle;
        }

        public String getmContentStr() {
            return mContentStr;
        }

        public void setmContentStr(String mContentStr) {
            this.mContentStr = mContentStr;
        }
    }
}
