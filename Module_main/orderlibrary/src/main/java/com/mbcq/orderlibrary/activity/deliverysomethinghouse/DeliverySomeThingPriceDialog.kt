package com.mbcq.orderlibrary.activity.deliverysomethinghouse

import android.graphics.Color
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.MoneyInputFilter
import com.mbcq.orderlibrary.R
import com.mbcq.orderlibrary.activity.acceptbilling.billingvolumecalculator.afterTextChanged
import kotlinx.android.synthetic.main.dialog_delevery_somethig_house_price.*
import org.json.JSONObject

class DeliverySomeThingPriceDialog(var mScreenWidth: Int = 0, var mOlderDataJson: String = "", var mClick: OnClickInterface.OnClickInterface? = null) : BaseDialogFragment() {
    override fun setContentView(): Int = R.layout.dialog_delevery_somethig_house_price

    override fun initView(view: View, savedInstanceState: Bundle?) {
        if (mOlderDataJson.isNotBlank()) {
            JSONObject(mOlderDataJson).also {
                fork_lift_fee_ed.setText(it.optString("accForklift"))//叉车费
                loading_fee_ed.setText(it.optString("accHanding"))//装卸费
                billing_delivery_fee_tv.setText(it.optString("accSend"))//开单送货费
                actual_delivery_fee_ed.setText(it.optString("accsend"))//实际送货费
                actual_number_ed.hint = it.optString("qty")//全部件数
                actual_number_ed.setText(it.optString("sendqty"))//实际件数
            }

        }
        actual_number_ed.afterTextChanged {
            if (actual_number_ed.text.toString().isBlank()) return@afterTextChanged
            if (actual_number_ed.text.toString().toInt() > JSONObject(mOlderDataJson).optString("qty").toInt()) {
                actual_number_ed.setTextColor(Color.RED)
            } else {
                if (actual_number_ed.currentTextColor != Color.BLACK) {
                    actual_number_ed.setTextColor(Color.BLACK)
                }
            }
        }
        actual_delivery_fee_ed.filters = arrayOf<InputFilter>(MoneyInputFilter()) //设置金额输入的过滤器，保证只能输入金额类型
        fork_lift_fee_ed.filters = arrayOf<InputFilter>(MoneyInputFilter()) //设置金额输入的过滤器，保证只能输入金额类型
        loading_fee_ed.filters = arrayOf<InputFilter>(MoneyInputFilter()) //设置金额输入的过滤器，保证只能输入金额类型
        commit_tv.apply {
            onSingleClicks {
                if (actual_number_ed.text.toString().isBlank()) {
                    ToastUtils.showToast(mContext, "请输入实际件数")
                    return@onSingleClicks

                }
                if (actual_number_ed.text.toString().toInt() > JSONObject(mOlderDataJson).optString("qty").toInt()) {
                    ToastUtils.showToast(mContext, "请检查实际件数")
                    return@onSingleClicks
                }
                if (actual_delivery_fee_ed.text.toString().isBlank()) {
                    ToastUtils.showToast(mContext, "请输入实际送货费")
                    return@onSingleClicks
                }
                if (fork_lift_fee_ed.text.toString().isBlank()) {
                    ToastUtils.showToast(mContext, "请输入叉车费")
                    return@onSingleClicks
                }
                if (loading_fee_ed.text.toString().isBlank()) {
                    ToastUtils.showToast(mContext, "请输入装卸费")
                    return@onSingleClicks
                }
                val obj = JSONObject()
                obj.put("accsend", actual_delivery_fee_ed.text.toString())//实际送货费
                obj.put("accForklift", fork_lift_fee_ed.text.toString())//叉车费
                obj.put("accHanding", loading_fee_ed.text.toString())//装卸费
                obj.put("sendqty", actual_number_ed.text.toString())//送货件数
                mClick?.onResult(GsonUtils.toPrettyFormat(obj), "")
                dismiss()

            }
        }
        dismiss_tv.apply {
            onSingleClicks {
                dismiss()
            }
        }
    }

    override fun setDialogWidth(): Int {
        return mScreenWidth / 5 * 4
    }

}