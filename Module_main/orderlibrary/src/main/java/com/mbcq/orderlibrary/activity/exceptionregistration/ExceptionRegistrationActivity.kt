package com.mbcq.orderlibrary.activity.exceptionregistration


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_exception_registration.*

/**
 * @author: lzy
 * @time: 2020-09-28 17:40:00 异常登记
 */
@Route(path=ARouterConstants.ExceptionRegistrationActivity)
class ExceptionRegistrationActivity : BaseMVPActivity<ExceptionRegistrationContract.View, ExceptionRegistrationPresenter>(), ExceptionRegistrationContract.View {
    override fun getLayoutId(): Int = R.layout.activity_exception_registration
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        exception_registration_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}