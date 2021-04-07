package com.mbcq.baselibrary.dialog.dialogfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.lang.reflect.Field;


public abstract class BaseDialogFragment extends DialogFragment {
    protected Context mContext;

    protected abstract void initView(@NonNull View view, @Nullable Bundle savedInstanceState);

    protected abstract int setContentView();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        mContext = getActivity();
        View mBaseView = inflater.inflate(setContentView(), container, false);
        return mBaseView;
    }


    @Override
    public void onStart() {
        super.onStart();
        Window window = getDialog().getWindow();
        if (window == null) return;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = setDialogGravity();
        params.width = setDialogWidth();
        params.height = setDialogHeight();
//        window.setDimAmount(0f);
        window.setAttributes(params);
        //设置背景透明
        if (setIsShowBackDark())
            window.setBackgroundDrawable(new ColorDrawable(setShowBackGround()));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getDialog() != null) {
            getDialog().setCancelable(setCancelable());
            getDialog().setCanceledOnTouchOutside(setCanceledOnTouchOutside());

        }
        initView(view, savedInstanceState);

    }

    /**
     * 如果显示闪退 用这个方法
     * 内部类有两个参数不公开 反射方法设置
     * @param manager
     * @param tag
     */
    public void showAllowingStateLoss(FragmentManager manager, String tag) {
        try {
            Field dismissed = DialogFragment.class.getDeclaredField("mDismissed");
            dismissed.setAccessible(true);
            dismissed.set(this, false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        try {
            Field shown = DialogFragment.class.getDeclaredField("mShownByMe");
            shown.setAccessible(true);
            shown.set(this, true);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        FragmentTransaction ft = manager.beginTransaction();
        ft.add(this, tag);
        ft.commitAllowingStateLoss();
    }

    /**
     * 想修改参数直接重写即可
     *
     * @return
     */
    protected boolean setIsShowBackDark() {
        return true;
    }

    protected int setShowBackGround() {
        return Color.TRANSPARENT;
    }

    protected boolean setCanceledOnTouchOutside() {
        return true;
    }

    protected boolean setCancelable() {
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
