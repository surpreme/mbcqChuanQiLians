package com.mbcq.baselibrary.view;


import android.util.Log;
import android.view.View;

/**
 * @Auther: liziyang
 * @datetime: 2020/4/27
 * @desc:
 */
public abstract class SingleClick implements View.OnClickListener {
    private long mLastClickTime;
    private long timeInterval = 1000L;

    public SingleClick() {

    }

    public SingleClick(long interval) {
        this.timeInterval = interval;
    }

    @Override
    public void onClick(View v) {
        long nowTime = System.currentTimeMillis();
        if (nowTime - mLastClickTime > timeInterval) {
            // 单次点击事件
            onSingleClick(v);
            mLastClickTime = nowTime;
        } else {
            // 快速点击事件
            onFastClick(v);
        }
    }

    protected abstract void onSingleClick(View v);

    protected void onFastClick(View v) {
        Log.d("SingleClick", "onFastClick: 用户快速点击");
    }
}