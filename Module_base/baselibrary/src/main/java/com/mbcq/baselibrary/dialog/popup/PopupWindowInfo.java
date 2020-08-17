package com.mbcq.baselibrary.dialog.popup;


import android.content.Context;
import android.widget.LinearLayout;

/**
 * @Auther: valy
 * @datetime: 2020/3/19
 * @desc:
 */
public class PopupWindowInfo {
    public int mWidth = LinearLayout.LayoutParams.WRAP_CONTENT;
    public int mHeight = LinearLayout.LayoutParams.WRAP_CONTENT;
    public int resId = 0;
    public boolean isOutsideTouchable = true;
    public boolean isDarkWindow = true;
    public boolean isFocusable = true;
    public Context mContext = null;

    @Override
    public String toString() {
        return "PopupWindowInfo{" +
                "mWidth=" + mWidth +
                ", mHeight=" + mHeight +
                ", resId=" + resId +
                ", isOutsideTouchable=" + isOutsideTouchable +
                ", isDarkWindow=" + isDarkWindow +
                ", isFocusable=" + isFocusable +
                ", mContext=" + mContext +
                '}';
    }
}

