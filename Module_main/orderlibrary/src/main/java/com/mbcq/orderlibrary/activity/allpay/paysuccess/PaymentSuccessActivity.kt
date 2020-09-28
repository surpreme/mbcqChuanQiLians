package com.mbcq.orderlibrary.activity.allpay.paysuccess


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_payment_success.*

/**
 * @author: lzy
 * @time: 2020-09-28 08:55:13
 */
@Route(path = ARouterConstants.PaymentSuccessActivity)
class PaymentSuccessActivity : BaseMVPActivity<PaymentSuccessContract.View, PaymentSuccessPresenter>(), PaymentSuccessContract.View {
    override fun getLayoutId(): Int = R.layout.activity_payment_success
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_gray)
        object : CountDownTimer(5 * 1000, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                countdown_tips_tv.text = "正在打印签收单（${(millisUntilFinished / 1000)}s）......."

            }

            override fun onFinish() {
                ARouter.getInstance().build(ARouterConstants.GoodsReceiptActivity).navigation()
                finish()

            }

        }.start()
    }

    override fun onClick() {
        super.onClick()
        pay_success_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}