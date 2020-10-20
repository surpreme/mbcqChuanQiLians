package com.mbcq.baselibrary.dialog.popup;


import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

import org.jetbrains.annotations.NotNull;


/**
 * @Auther: valy
 * @datetime: 2020/3/19
 * @desc:
 */
public abstract class BasePopupWindows {
    public PopupWindowInfo popupWindowInfo;
    protected PopupWindow popupWindow;
    protected Context context;

    public abstract void initView(@NotNull View view);

    protected void dismissListener() {
        if (popupWindowInfo.isDarkWindow) {
            setBackGroundAlpha(1.0f, context);
        }
    }

    public BasePopupWindows(@NotNull Context mContext) {
        this.context = mContext;
    }

    public void dismiss() {
        if (popupWindow != null && popupWindow.isShowing()) {
            setBackGroundAlpha(1.0f, context);
            popupWindow.dismiss();
        }
    }

    public BasePopupWindows show(View view) {
        View contentView = LayoutInflater.from(context).inflate(popupWindowInfo.resId, null);
        initView(contentView);
        popupWindow = new PopupWindow(contentView, popupWindowInfo.mWidth, popupWindowInfo.mHeight);
        popupWindow.setOutsideTouchable(popupWindowInfo.isOutsideTouchable);
//        if (popupWindowInfo.isDarkWindow)
//            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        if (popupWindowInfo.isDarkWindow) {
            setBackGroundAlpha(0.6f, context);
        }
        popupWindow.setFocusable(popupWindowInfo.isFocusable);
        popupWindow.setOnDismissListener(() -> dismissListener());
        popupWindow.showAsDropDown(view);
        return this;
    }
    public BasePopupWindows showUp(View view) {
        View contentView = LayoutInflater.from(context).inflate(popupWindowInfo.resId, null);
        initView(contentView);
        popupWindow = new PopupWindow(contentView, popupWindowInfo.mWidth, popupWindowInfo.mHeight);
        popupWindow.setOutsideTouchable(popupWindowInfo.isOutsideTouchable);
//        if (popupWindowInfo.isDarkWindow)
//            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        if (popupWindowInfo.isDarkWindow) {
            setBackGroundAlpha(0.6f, context);
        }
        //获取自身的长宽高
        contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int popupHeight = contentView.getMeasuredHeight();
        int popupWidth = contentView.getMeasuredWidth();
        popupWindow.setFocusable(popupWindowInfo.isFocusable);
        popupWindow.setOnDismissListener(() -> dismissListener());
        //获取需要在其上方显示的控件的位置信息
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        //在控件上方显示
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY,  (location[0] + view.getWidth() / 2) - popupWidth / 2, location[1] - popupHeight);

//        popupWindow.showAsDropDown(view, x, y);
        return this;
    }

    public BasePopupWindows show(int gravity) {
        View contentView = LayoutInflater.from(context).inflate(popupWindowInfo.resId, null);
        initView(contentView);
        popupWindow = new PopupWindow(contentView, popupWindowInfo.mWidth, popupWindowInfo.mHeight);
        popupWindow.setOutsideTouchable(popupWindowInfo.isOutsideTouchable);
//        if (popupWindowInfo.isDarkWindow)
//            popupWindow.setBackgroundDrawable(new ColorDrawable(Color.GRAY));
        if (popupWindowInfo.isDarkWindow) {
            setBackGroundAlpha(0.6f, context);
        }
        popupWindow.setFocusable(popupWindowInfo.isFocusable);
        popupWindow.setOnDismissListener(() -> dismissListener());
        popupWindow.showAtLocation(contentView, gravity, 0, 0);
        return this;
    }

    void setBackGroundAlpha(float alpha, Context context) {
        if (!(context instanceof Activity)) return;
        try {
            WindowManager.LayoutParams layoutParams = ((Activity) context).getWindow().getAttributes();
            layoutParams.alpha = alpha;
            ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
            ((Activity) context).getWindow().setAttributes(layoutParams);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}

