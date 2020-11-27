package com.mbcq.accountlibrary.fragment.settingadapter;

import java.util.List;

public class SettingIconBean {
    private int tag;
    private String title;
    private String contentText="";
    private List<ItemBean> iconItemBean;


    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public String getContentText() {
        return contentText;
    }

    public void setContentText(String contentText) {
        this.contentText = contentText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<ItemBean> getIconItemBean() {
        return iconItemBean;
    }

    public void setIconItemBean(List<ItemBean> iconItemBean) {
        this.iconItemBean = iconItemBean;
    }


    public static class ItemBean {
        private int imgId;
        private String showTxt;

        public String getShowTxt() {
            return showTxt;
        }

        public void setShowTxt(String showTxt) {
            this.showTxt = showTxt;
        }

        public int getImgId() {
            return imgId;
        }

        public void setImgId(int imgId) {
            this.imgId = imgId;
        }
    }
}
