package com.mbcq.baselibrary.util.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.mbcq.baselibrary.R;

public class ToastUtils {
    private static Toast toast;

    @SuppressLint({"ShowToast", "ResourceType"})
    public static void showToast(Context context, String msg) {
        if (context == null && msg == null) return;
        if (toast == null) {
            toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    /**
     * TODO
     *
     * @param view
     * @param msg
     * @param onClickListener
     */
    public static void showSnakbar(View view, String msg, View.OnClickListener onClickListener) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("确定", onClickListener).show();
    }

}
