package com.mbcq.baselibrary.dialog.popup;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

/**
 * lzy封装 不喜勿删 请尊重他人改了几版的结晶
 * 如果喜欢可以再拓展 这里把采用builder模式加java思想实现 实现了各个逻辑模块的抽离
 * 可能文件有点多 遵循rxjava思想
 * 同时也有dialogfragment作为谷歌推荐的弹窗
 */
public class XDialog {
    // TODO: 2020/3/19 全局弹窗的配置
    private String mainColor;

    public String getMainColor() {
        return mainColor;
    }

    public void setMainColor(String mainColor) {
        this.mainColor = mainColor;
    }

    public XDialog() {

    }

    public static class Builder {
        private final PopupWindowInfo popupWindowInfo = new PopupWindowInfo();
        private Context mContext;

        public Builder(@NotNull Context activity) {
            mContext = activity;
        }

        public Builder setContentView(int reslayout) {
            this.popupWindowInfo.resId = reslayout;
            return this;
        }

        public Builder setIsDarkWindow(boolean isDarkWindow) {
            this.popupWindowInfo.isDarkWindow = isDarkWindow;
            return this;
        }

        public Builder setOutsideTouchable(boolean ok) {
            this.popupWindowInfo.isOutsideTouchable = ok;
            return this;
        }

        public Builder setFocusable(boolean focusable) {
            this.popupWindowInfo.isFocusable = focusable;
            return this;
        }

        public Builder setWidth(int mWidth) {
            this.popupWindowInfo.mWidth = mWidth;
            return this;
        }

        public Builder setHeight(int mHeight) {
            this.popupWindowInfo.mHeight = mHeight;
            return this;
        }

        /**
         * public CenterListPopupView asCenterList(String title, String[] data, int[] iconIds, int checkedPosition, OnSelectListener selectListener) {
         * popupType(PopupType.Center);
         * CenterListPopupView popupView = new CenterListPopupView(this.context)
         * .setStringData(title, data, iconIds)
         * .setCheckedPosition(checkedPosition)
         * .setOnSelectListener(selectListener);
         * popupView.popupInfo = this.popupInfo;
         * return popupView;
         * }
         *   OveringTimeDialog overingTimeDialog=new OveringTimeDialog(mContext,"短信验证码已经发送,五分钟内有效,\n请勿操作频繁!");
         *         new XDialog.Builder(mContext).setContentView(R.layout.dialog_overingtime)
         *                 .setWidth(getScreenWidth()/4*3)
         *                 .setIsDarkWindow(true)
         *                 .setOutsideTouchable(false)
         *                 .asCustom(overingTimeDialog)
         *                 .show(Gravity.CENTER);
         * TODO: 2020/3/19 这里写一个类去实现
         */


        public BasePopupWindows asCustom(BasePopupWindows basePopupWindows) {
            basePopupWindows.popupWindowInfo = popupWindowInfo;
            return basePopupWindows;
        }
    }
}
