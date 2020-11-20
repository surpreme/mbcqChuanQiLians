package com.mbcq.amountlibrary.activity.generatepaymentvoucher


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.bigkoo.pickerview.listener.OnTimeSelectListener
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.ui.mvp.BaseMVPActivity
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.commonlibrary.dialog.TimeDialogUtil
import kotlinx.android.synthetic.main.activity_generate_payment_voucher.*
import java.text.DateFormat
import java.text.SimpleDateFormat

/**
 * @author: lzy
 * @time: 2020-11-20 13:10:12 生成付款凭证
 */

@Route(path = ARouterConstants.GeneratePaymentVoucherActivity)
class GeneratePaymentVoucherActivity : BaseMVPActivity<GeneratePaymentVoucherContract.View, GeneratePaymentVoucherPresenter>(), GeneratePaymentVoucherContract.View {
    override fun getLayoutId(): Int = R.layout.activity_generate_payment_voucher
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
    }

    override fun onClick() {
        super.onClick()
        voucher_date_ll.setOnClickListener(object :SingleClick(){
            @SuppressLint("SimpleDateFormat")
            override fun onSingleClick(v: View?) {
                TimeDialogUtil.getChoiceTimer(mContext, OnTimeSelectListener { date, _ ->
                    val mDateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
                    val format: String = mDateFormat.format(date)
                    voucher_date_tv.text = format

                }, "选择凭证日期", isStartCurrentTime = false, isEndCurrentTime = false, isYear = true, isHM = true, isDialog = false).show(voucher_date_ll)
            }

        })
        generate_payment_voucher_toolbar.setBackButtonOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }
}