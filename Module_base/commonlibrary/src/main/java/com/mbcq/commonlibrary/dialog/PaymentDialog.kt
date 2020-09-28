package com.mbcq.commonlibrary.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.R
import kotlinx.android.synthetic.main.dialog_payment_method.*

class PaymentDialog : BaseDialogFragment {
    constructor(mOnSelectPaymentMethodInterface: OnSelectPaymentMethodInterface) {
        this.mOnSelectPaymentMethodInterface = mOnSelectPaymentMethodInterface
    }

    var mOnSelectPaymentMethodInterface: OnSelectPaymentMethodInterface? = null


    interface OnSelectPaymentMethodInterface {
        fun onResult(v: View, result: Int)
    }

    override fun initView(view: View, savedInstanceState: Bundle?) {
        wechat_pay_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mOnSelectPaymentMethodInterface?.onResult(v, 1)
                dismiss()
            }

        })
        aplipay_pay_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mOnSelectPaymentMethodInterface?.onResult(v, 2)
                dismiss()
            }

        })
        ic_cash_payment_ll.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View) {
                mOnSelectPaymentMethodInterface?.onResult(v, 3)
                dismiss()
            }

        })
    }

    override fun setCancelable(): Boolean = true
    override fun setCanceledOnTouchOutside(): Boolean = true
    override fun setContentView(): Int = R.layout.dialog_payment_method
    override fun setDialogGravity(): Int = Gravity.BOTTOM
    override fun setDialogWidth(): Int = WindowManager.LayoutParams.MATCH_PARENT
}