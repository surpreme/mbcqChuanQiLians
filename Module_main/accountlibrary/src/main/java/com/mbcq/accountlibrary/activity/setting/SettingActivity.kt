package com.mbcq.accountlibrary.activity.setting

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.accountlibrary.R
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_setting.*

/**
 * 设置页面
 */
@Route(path = ARouterConstants.SettingActivity)
class SettingActivity : BaseActivity() {
    override fun getLayoutId(): Int = R.layout.activity_setting
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }
    override fun onClick() {
        super.onClick()
        setting_activity_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
    }

}