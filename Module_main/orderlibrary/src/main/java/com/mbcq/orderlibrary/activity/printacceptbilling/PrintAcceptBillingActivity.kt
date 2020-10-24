package com.mbcq.orderlibrary.activity.printacceptbilling


import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.view.SingleClick
import com.mbcq.commonlibrary.ARouterConstants
import com.mbcq.orderlibrary.R
import kotlinx.android.synthetic.main.activity_print_accept_billing.*
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-24 14:18:42 运单标签补打印
 */

@Route(path = ARouterConstants.PrintAcceptBillingActivity)
class PrintAcceptBillingActivity : BaseBlueToothPrintAcceptBillingActivity<PrintAcceptBillingContract.View, PrintAcceptBillingPresenter>(), PrintAcceptBillingContract.View {
    override fun getLayoutId(): Int = R.layout.activity_print_accept_billing
    override fun initViews(savedInstanceState: Bundle?) {
        super.initViews(savedInstanceState)
        setStatusBar(R.color.base_blue)
        clearInfo()

    }

    override fun onClick() {
        super.onClick()
        print_btn.setOnClickListener(object : SingleClick(4000) {
            override fun onSingleClick(v: View?) {
                if (mWayBillInfoJson.isBlank() or mWayBillInfoJson.contains("{}")) {
                    TalkSureDialog(mContext, getScreenWidth(), "请输入您的运单信息").show()
                    return
                }
                if (label_checkbox.isChecked && consignment_checkbox.isChecked) {
                    val printAdapter = getZpBluetoothPrinter()
                    print_YH_TYD_NEW1(Gson().fromJson(mWayBillInfoJson, PrintAcceptBillingBean::class.java), false, UserInformationUtil.getWebIdCodeStr(mContext), mWayBillInfoJObj, printAdapter)
                    print_LabelTemplated_XT423(Gson().fromJson(mWayBillInfoJson, PrintAcceptBillingBean::class.java), 1, printAdapter)
                }
            }

        })

        search_btn.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                if (billno_ed.text.toString().isBlank()) {
                    TalkSureDialog(mContext, getScreenWidth(), "请输入运单号进行查询").show()
                    return
                }
                mPresenter?.getWayBillInfo(billno_ed.text.toString())
            }

        })
        print_accept_billing_toolbar.setOnClickListener(object : SingleClick() {
            override fun onSingleClick(v: View?) {
                onBackPressed()
            }

        })
    }

    @SuppressLint("SetTextI18n")
    fun clearInfo() {
        waybill_info_tv.text = "运  单 号：\n货      号：\n货物名称：\n包      装：\n发货网点：\n到货网点：\n收  货 人：\n件      数："
        mWayBillInfoJson = "{}"
        mWayBillInfoJObj = JSONObject()
    }

    var mWayBillInfoJson = "{}"
    var mWayBillInfoJObj = JSONObject()

    @SuppressLint("SetTextI18n")
    override fun getWayBillInfoS(data: JSONObject, dataStr: String) {
        waybill_info_tv.text = "运  单 号：${data.optString("billno")}\n货      号：${data.optString("goodsNum")}\n货物名称：${data.optString("product")}\n包      装：${data.optString("packages")}\n发货网点：${data.optString("webidCodeStr")}\n到货网点：${data.optString("ewebidCodeStr")}\n收  货 人：${data.optString("consignee")}\n件      数：${data.optString("qty")}"
        mWayBillInfoJson = dataStr
        mWayBillInfoJObj = data
    }

    override fun getWayBillInfoNull() {
        TalkSureDialog(mContext, getScreenWidth(), "未查询到运单，请确认输入运单号是否有误！").show()
        clearInfo()
    }
}