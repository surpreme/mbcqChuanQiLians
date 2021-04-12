package com.mbcq.baselibrary.util.system;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @Auther: lzy
 * @datetime: 2020/5/16
 * @desc:
 */
public class SoftKeyboardUtil {
    //收起输入法
    public static void closeKeyboard(Activity activity) {
        View view = activity.getWindow().peekDecorView();
        if (view != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void hideKeyboard(Context mContext, View view) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    /**
     * 隐藏/显示输入法
     */
    public static void showAndHideInput(final EditText editText, boolean show) {
        editText.setFocusable(show);
        editText.setFocusableInTouchMode(show);
        editText.requestFocus();
        final InputMethodManager inputManager = (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (show) {
            //打开键盘
            editText.postDelayed(new Runnable() {
                @Override
                public void run() {
                    inputManager.showSoftInput(editText, 0);
                }
            }, 200);
        } else {
            //关闭软键盘
            inputManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
        }
    }
    public static void showSoftInputFromWindow(EditText et_text) {
        et_text.setFocusable(true);
        et_text.setFocusableInTouchMode(true);
        et_text.requestFocus();
        InputMethodManager inputManager =
                (InputMethodManager) et_text.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(et_text, 0);

    }
}
