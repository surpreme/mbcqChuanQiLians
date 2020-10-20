package com.mbcq.baselibrary.dialog.common;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.mbcq.baselibrary.R;
import com.mbcq.baselibrary.dialog.system.BaseSystemDialog;

public class TalkSureCancelDialog extends BaseSystemDialog {
    private int mScreenWidth;
    private String mTips;

    public void setOnClickInterface(OnClickInterface onClickInterface) {
        this.onClickInterface = onClickInterface;
    }

    private OnClickInterface onClickInterface;

    public TalkSureCancelDialog(@NonNull Context context, int mScreenWidth, String mTips) {
        super(context);
        this.mTips = mTips;
        this.mScreenWidth = mScreenWidth;
    }

    public TalkSureCancelDialog(@NonNull Context context, int mScreenWidth, String mTips, OnClickInterface onClickInterface) {
        super(context);
        this.mTips = mTips;
        this.mScreenWidth = mScreenWidth;
        this.onClickInterface = onClickInterface;

    }

    @Override
    protected int setContentViews() {
        return R.layout.dialog_sure_cancel_talk;
    }

    @Override
    protected int setDialogWidth() {
        return mScreenWidth / 4 * 3;
    }

    @Override
    protected void initView(View view) {
        TextView information_tv = view.findViewById(R.id.information_tv);
        Button sure_btn = view.findViewById(R.id.sure_btn);
        Button cancel_btn = view.findViewById(R.id.cancel_btn);
        information_tv.setText(mTips);
        cancel_btn.setOnClickListener(v -> {
            dismiss();
        });
        sure_btn.setOnClickListener(v -> {
            /**
             * 内部方法銷毀
             *  dismiss();
             */


            if (onClickInterface != null)
                onClickInterface.onClick(v);
            dismiss();

        });

    }

    public interface OnClickInterface {
        void onClick(View view);
    }
}
