package com.mbcq.baselibrary.util.system;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class ToastUtils {
    private static Toast toast;

    @SuppressLint("ShowToast")
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
     * @param view
     * @param msg
     * @param onClickListener
     */
    public static void showSnakbar(View view, String msg, View.OnClickListener onClickListener) {
        Snackbar.make(view, msg, Snackbar.LENGTH_LONG).setAction("确定", onClickListener).show();
    }

}
