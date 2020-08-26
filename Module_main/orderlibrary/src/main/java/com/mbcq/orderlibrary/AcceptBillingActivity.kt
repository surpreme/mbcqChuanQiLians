package com.mbcq.orderlibrary

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.BaseActivity
import com.mbcq.commonlibrary.ARouterConstants
import kotlinx.android.synthetic.main.activity_accept_billing.*

@Route(path = ARouterConstants.AcceptBillingActivity)
class AcceptBillingActivity :BaseActivity(){
    override fun getLayoutId(): Int =R.layout.activity_accept_billing
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        accept_billing_toolbar.setBackButtonOnClickListener {
            onBackPressed()
        }
    }
}