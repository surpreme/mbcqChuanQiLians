package com.mbcq.baselibrary.dialog.system;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;

public abstract class BaseSystemDialog extends Dialog {
    protected Context context;
    protected abstract int setContentViews();
    protected abstract void initView(View view);
    public BaseSystemDialog(@NonNull Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(setContentViews(), null);
        setContentView(view);
        initView(view);
        setCancelable(setIsCancel());
        setCanceledOnTouchOutside(setIsOutSideCancel());
    }

    @Override
    protected void onStart() {
        super.onStart();
        Window window = getWindow();
        if (window==null)return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity =setDialogGravity(); //居中显示
        params.width = setDialogWidth();
        params.height = setDialogHeight();
        if (isShowDarkBackGround())
            window.setBackgroundDrawableResource(android.R.color.transparent);
        window.setAttributes(params);

    }
    protected boolean setIsCancel(){
        return false;
    }
    protected boolean setIsOutSideCancel(){
        return false;
    }
    protected boolean isShowDarkBackGround(){
        return true;
    }
    protected int setDialogGravity() {
        return Gravity.CENTER;
    }

    protected int setDialogWidth() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }

    protected int setDialogHeight() {
        return WindowManager.LayoutParams.WRAP_CONTENT;
    }
}
