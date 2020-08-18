package com.mbcq.chuanqilian

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.commonlibrary.ARouterConstants

/**
 * 开屏页
 */
class MainActivity : BaseActivity() {
    override fun getLayoutId(): Int =R.layout.activity_main

    override fun initViews() {
        super.initViews()
        ARouter.getInstance().build(ARouterConstants.LogInActivity).navigation()

    }
}