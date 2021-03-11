package com.mbcq.accountlibrary.fragment.iconadapter;

import java.util.List;

public class IconViewBean {
    private int tag;
    private List<ItemBean> item;
    private String title = "";
    private String authCode = "";

    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public static class ItemBean {
        private String itemText;
        private String arouterAddress = "";
        private String authCode = "";
        private String arouterOrtherInfo = "";

        public String getArouterOrtherInfo() {
            return arouterOrtherInfo;
        }

        public void setArouterOrtherInfo(String arouterOrtherInfo) {
            this.arouterOrtherInfo = arouterOrtherInfo;
        }


        public String getItemText() {
            return itemText;
        }

        public void setItemText(String itemText) {
            this.itemText = itemText;
        }

        public String getArouterAddress() {
            return arouterAddress;
        }
        public String getAuthCode() {
            return authCode;
        }

        public void setAuthCode(String authCode) {
            this.authCode = authCode;
        }

        public void setArouterAddress(String arouterAddress) {
            this.arouterAddress = arouterAddress;
        }
    }

}
