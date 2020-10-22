package com.mbcq.baselibrary.view;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mbcq.baselibrary.util.screen.ScreenSizeUtils;

public class CustomizeToastUtil {
    private Toast toast;
    private LinearLayout toastView;

    /**
     * 修改原布局的Toast
     */
    public CustomizeToastUtil() {

    }

    /**
     * 完全自定义布局Toast
     *
     * @param context
     * @param view
     */
    public CustomizeToastUtil(Context context, View view, int duration) {
        toast = new Toast(context);
        toast.setView(view);
        toast.setDuration(duration);
    }

    /**
     * 向Toast中添加自定义view
     *
     * @param view
     * @param postion
     * @return
     */
    public CustomizeToastUtil addView(View view, int postion) {
        toastView = (LinearLayout) toast.getView();
        toastView.addView(view, postion);

        return this;
    }

    /**
     * 设置Toast字体及背景颜色
     *
     * @param messageColor
     * @param backgroundColor
     * @return
     */
    public CustomizeToastUtil setToastColor(int messageColor, int backgroundColor) {
        View view = toast.getView();
        if (view != null) {
            TextView message = ((TextView) view.findViewById(android.R.id.message));
            message.setBackgroundColor(backgroundColor);
            message.setTextColor(messageColor);
        }
        return this;
    }

    public CustomizeToastUtil setGravity(int gravity) {
        View view = toast.getView();
        if (view != null) {
            toast.setGravity(gravity, 0, 70);
        }
        return this;
    }

    /**
     * 设置Toast字体及背景
     *
     * @param messageColor
     * @param background
     * @return
     */
    public CustomizeToastUtil setToastBackground(int messageColor, int background) {
        View view = toast.getView();
        if (view != null) {
            TextView message = ((TextView) view.findViewById(android.R.id.message));
//            message.setBackgroundResource(background);
            message.setPadding(60,10,60,10);
            view.setBackgroundResource(background);
            view.getBackground().setAlpha(210);//0~255透明度值
//            message.getBackground().setAlpha(150);//0~255透明度值
            message.setTextColor(messageColor);
        }
        return this;
    }

    /**
     * 短时间显示Toast
     */
    public CustomizeToastUtil Short(Context context, CharSequence message) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        return this;
    }

    /**
     * 短时间显示Toast
     */
    public CustomizeToastUtil Short(Context context, int message) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_SHORT);
        }
        return this;
    }

    /**
     * 长时间显示Toast
     */
    public CustomizeToastUtil Long(Context context, CharSequence message) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        return this;
    }

    /**
     * 长时间显示Toast
     *
     * @param context
     * @param message
     */
    public CustomizeToastUtil Long(Context context, int message) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, Toast.LENGTH_LONG);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(Toast.LENGTH_LONG);
        }
        return this;
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public CustomizeToastUtil Indefinite(Context context, CharSequence message, int duration) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, duration);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return this;
    }

    /**
     * 自定义显示Toast时间
     *
     * @param context
     * @param message
     * @param duration
     */
    public CustomizeToastUtil Indefinite(Context context, int message, int duration) {
        if (toast == null || (toastView != null && toastView.getChildCount() > 1)) {
            toast = Toast.makeText(context, message, duration);
            toastView = null;
        } else {
            toast.setText(message);
            toast.setDuration(duration);
        }
        return this;
    }

    /**
     * 显示Toast
     *
     * @return
     */
    public CustomizeToastUtil show() {
        toast.show();

        return this;
    }

    /**
     * 获取Toast
     *
     * @return
     */
    public Toast getToast() {
        return toast;
    }
}
