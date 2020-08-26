package com.mbcq.accountlibrary.fragment.operation;

import java.util.List;

public class OperationViewBean {
    private int tag;
    private List<ItemBean> item;
    private String title;
    public List<ItemBean> getItem() {
        return item;
    }

    public void setItem(List<ItemBean> item) {
        this.item = item;
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

        public String getItemText() {
            return itemText;
        }

        public void setItemText(String itemText) {
            this.itemText = itemText;
        }
    }

}
