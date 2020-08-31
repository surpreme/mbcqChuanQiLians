package com.mbcq.chuanqilian

import android.os.Bundle
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.finger.FingerprintHelper
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.commonlibrary.ARouterConstants

/**
 * 开屏页
 */
class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int =R.layout.activity_main

    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
//        ARouter.getInstance().build(ARouterConstants.LogInActivity).withFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation()
        ARouter.getInstance().build(ARouterConstants.LogInActivity).navigation()
        finish()


    }
}