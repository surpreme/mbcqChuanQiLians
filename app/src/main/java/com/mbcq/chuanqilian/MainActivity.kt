package com.mbcq.chuanqilian

import android.os.Bundle
import android.os.CountDownTimer
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.finger.FingerprintHelper
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.baselibrary.util.screen.ScreenSizeUtils
import com.mbcq.baselibrary.util.screen.StatusBarUtils
import com.mbcq.commonlibrary.ARouterConstants

/**
 * 开屏页
 */
class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int = R.layout.activity_main
    override fun onResume() {
        super.onResume()
        if (ScreenSizeUtils.checkDeviceHasNavigationBar(mContext))
            StatusBarUtils.hideSystemNavigationBar(mContext)
    }

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)

//        ARouter.getInstance().build(ARouterConstants.LogInActivity).withFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation()
        object : CountDownTimer(2000, 2000) {
            override fun onTick(millisUntilFinished: Long) {

            }

            override fun onFinish() {
                ARouter.getInstance().build(ARouterConstants.LogInActivity).navigation()
                finish()
            }

        }.start()


    }
}