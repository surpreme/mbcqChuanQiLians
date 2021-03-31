package com.mbcq.vehicleslibrary.activity.homedeliveryhouse

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.MoneyInputFilter
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.dialog_home_delevery_price.*
import org.json.JSONObject

class HomeDeliveryHousePriceDialog(var mScreenWidth: Int = 0, var mOlderDataJson: String = "", var mClick: OnClickInterface.OnClickInterface? = null) : BaseDialogFragment() {
    override fun setContentView(): Int = R.layout.dialog_home_delevery_price

    override fun initView(view: View, savedInstanceState: Bundle?) {
        if (mOlderDataJson.isNotBlank()){
            fork_lift_fee_ed.setText(JSONObject(mOlderDataJson).optString("accCc"))
            loading_fee_ed.setText(JSONObject(mOlderDataJson).optString("accZxf"))
        }
        fork_lift_fee_ed.filters = arrayOf<InputFilter>(MoneyInputFilter()) //设置金额输入的过滤器，保证只能输入金额类型
        loading_fee_ed.filters = arrayOf<InputFilter>(MoneyInputFilter()) //设置金额输入的过滤器，保证只能输入金额类型
        commit_tv.apply {
            onSingleClicks {
                if (fork_lift_fee_ed.text.toString().isBlank()) {
                    ToastUtils.showToast(mContext, "请输入叉车费")
                    return@onSingleClicks
                }
                if (loading_fee_ed.text.toString().isBlank()) {
                    ToastUtils.showToast(mContext, "请输入装卸费")
                    return@onSingleClicks
                }
                val obj = JSONObject()
                obj.put("accCc", fork_lift_fee_ed.text.toString())
                obj.put("accZxf", loading_fee_ed.text.toString())
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