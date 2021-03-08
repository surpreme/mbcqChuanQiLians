package com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse

import android.os.Bundle
import android.text.InputFilter
import android.view.View
import com.mbcq.baselibrary.dialog.dialogfragment.BaseDialogFragment
import com.mbcq.baselibrary.dialog.popup.XDialog
import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.interfaces.OnClickInterface
import com.mbcq.baselibrary.view.MoneyInputFilter
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.adapter.BaseTextAdapterBean
import com.mbcq.vehicleslibrary.R
import kotlinx.android.synthetic.main.dialog_local_gent_transit_company.*
import org.json.JSONObject

/**
 * 本地代理中转信息配置
 */
class LocalGentShortFeederHouseTransitCompanyConfigurationDialog(var listDatas: List<BaseTextAdapterBean>, var mShowAgain: String, var mOnClickInterface: OnClickInterface.OnClickInterface? = null) : BaseDialogFragment() {
    override fun setContentView(): Int = R.layout.dialog_local_gent_transit_company

    override fun initView(view: View, savedInstanceState: Bundle?) {
        transit_fee_ed.filters = arrayOf<InputFilter>(MoneyInputFilter())
        transit_company_tv.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                XDialog.Builder(mContext)
                        .setContentView(R.layout.popup_local_gent_transit_company)
                        .asCustom(LocalGentShortFeederHouseTransitCompanyPopupWindows(mContext, listDatas).also {
                            it.mOnClickInterface = object : OnClickInterface.OnClickInterface {
                                override fun onResult(s1: String, s2: String) {
                                    val obj = JSONObject(s1)
                                    transit_company_tv.text = obj.optString("caruniname")
                                    transfer_call_ed.setText(obj.optString("mb"))
                                }

                            }
                        })
                        .show(transit_company_tv)
            }

        })
        cancel_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                dismiss()
            }

        })
        if (mShowAgain.isNotBlank()){
            val mShowAgainObj = JSONObject(mShowAgain)
            transit_company_tv.text = mShowAgainObj.optString("outCygs")
            transit_fee_ed.setText(mShowAgainObj.optString("outacc"))
            transfer_order_number_ed.setText(mShowAgainObj.optString("outbillno"))
            transfer_call_ed.setText(mShowAgainObj.optString("contactmb"))
        }


        sure_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                val obj = JSONObject()
                obj.put("outCygs", transit_company_tv.text.toString())//中转公司
                obj.put("outacc", transit_fee_ed.text.toString())//中转费
                obj.put("outbillno", transfer_order_number_ed.text.toString())//中转单号
                obj.put("contactmb", transfer_call_ed.text.toString())//中转手机号
                mOnClickInterface?.onResult(GsonUtils.toPrettyFormat(obj), "")
                dismiss()
            }

        })
    }

}