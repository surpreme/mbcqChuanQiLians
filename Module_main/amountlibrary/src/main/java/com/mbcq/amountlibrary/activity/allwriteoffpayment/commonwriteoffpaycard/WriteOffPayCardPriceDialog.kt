package com.mbcq.amountlibrary.activity.allwriteoffpayment.commonwriteoffpaycard

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import com.google.gson.Gson
import com.mbcq.amountlibrary.R
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.ui.onSingleClicks
import com.mbcq.baselibrary.util.system.ToastUtils
import com.mbcq.baselibrary.view.MoneyInputFilter
import kotlinx.android.synthetic.main.dialog_wrrite_off_pay_card_price.*
import org.json.JSONObject

class WriteOffPayCardPriceDialog(var mScreenWidth: Int = 0,
                                 var maxPriceStr: String = "",
                                 var priceStr: String = "",
                                 var remarkStr: String = "",
                                 var mClick: OnClickInterface.OnClickInterface) : BaseDialogFragment() {
    override fun setDialogWidth(): Int = mScreenWidth / 4 * 3

    override fun setContentView(): Int = R.layout.dialog_wrrite_off_pay_card_price

    override fun initView(view: View, savedInstanceState: Bundle?) {
        price_fee_ed.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (price_fee_ed.text.toString().isBlank()) return
                if (price_fee_ed.text.toString().toDouble() > maxPriceStr.toDouble()) {
                    price_fee_ed.setTextColor(Color.RED)
                } else {
                    if (price_fee_ed.currentTextColor != Color.BLACK) {
                        price_fee_ed.setTextColor(Color.BLACK)
                    }
                }

            }

        })
        price_fee_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        price_fee_ed.hint = maxPriceStr
        price_fee_ed.setText(priceStr)
        summary_ed.setText(remarkStr)
        commit_tv.apply {
            onSingleClicks {
                if (price_fee_ed.text.toString().isBlank()) {
                    ToastUtils.showToast(mContext, "请输入金额")
                    return@onSingleClicks
                }
                if (summary_ed.text.toString().isBlank()) {
                    ToastUtils.showToast(mContext, "请输入摘要")
                    return@onSingleClicks
                }
                if (maxPriceStr.toDouble() < price_fee_ed.text.toString().toDouble()) {
                    ToastUtils.showToast(mContext, "最大输入$maxPriceStr")
                    return@onSingleClicks
                }
                val obj = JSONObject()
                obj.put("accArrivedAfter", price_fee_ed.text.toString())
                obj.put("summary", summary_ed.text.toString())
                mClick.onResult(GsonUtils.toPrettyFormat(obj), "")
                dismiss()
            }
        }
        dismiss_tv.apply {
            onSingleClicks {
                dismiss()
            }
        }
    }

}