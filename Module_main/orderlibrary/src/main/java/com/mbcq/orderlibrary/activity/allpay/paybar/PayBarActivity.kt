package com.mbcq.orderlibrary.activity.allpay.paybar


import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_pay_bar.*

/**
 * @author: lzy
 * @time: 2020-09-28 08:38:00
 * 支付扫码页面
 */
@Route(path = ARouterConstants.PayBarActivity)
class PayBarActivity : BaseMVPActivity<PayBarContract.View, PayBarPresenter>(), PayBarContract.View {
    override fun getLayoutId(): Int = R.layout.activity_pay_bar
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        qr_scan_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                ARouter.getInstance().build(ARouterConstants.PaymentSuccessActivity).navigation()
            }

        })

        pay_bar_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}