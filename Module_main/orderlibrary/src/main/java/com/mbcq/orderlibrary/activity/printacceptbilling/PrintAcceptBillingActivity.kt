package com.mbcq.orderlibrary.activity.printacceptbilling


import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.gson.Gson
import com.mbcq.baselibrary.dialog.common.TalkSureDialog
import com.mbcq.baselibrary.ui.mvp.UserInformationUtil
import com.mbcq.baselibrary.util.log.LogUtils
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
                if (!isInteger(end_print_num_ed.text.toString())) {
                    TalkSureDialog(mContext, getScreenWidth(), "计算出错，点击返回") {
                        onBackPressed()
                    }.show()
                    return
                }

                if (mPrintMaxNum < end_print_num_ed.text.toString().toInt()) {
                    showToast("请检查您输入的数量和运单信息是否匹配")
                    return
                }
                if (mPrintMaxNum < start_print_num_ed.text.toString().toInt()) {
                    showToast("请检查您输入的数量和运单信息是否匹配")
                    return
                }
                if (start_print_num_ed.text.toString().toInt() > start_print_num_ed.text.toString().toInt()) {
                    showToast("请检查您输入开始数量和结束数量")
                    return
                }
                showLoading()
                object : CountDownTimer(1000, 1000) {
                    override fun onTick(millisUntilFinished: Long) {

                    }

                    override fun onFinish() {
                        if (isDestroyed)
                            return
                        try {
                            val printAdapter = getZpBluetoothPrinter()
                            if (consignment_checkbox.isChecked) {
                                print_YH_TYD_NEW1(Gson().fromJson(mWayBillInfoJson, PrintAcceptBillingBean::class.java), false, UserInformationUtil.getWebIdCodeStr(mContext), mWayBillInfoJObj, printAdapter)
                            }
                            if (label_checkbox.isChecked) {
                                printMoreLabel(Gson().fromJson(mWayBillInfoJson, PrintAcceptBillingBean::class.java), start_print_num_ed.text.toString().toInt(), end_print_num_ed.text.toString().toInt(), printAdapter)

                            }
                            closePrint(printAdapter)
                            closeLoading()
                            TalkSureDialog(mContext, getScreenWidth(), "打印完成").show()
                        } catch (e: Exception) {
                            e.printStackTrace()
                            LogUtils.e(e)
                            closeLoading()
                        }
                    }

                }.start()


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
        mPrintMaxNum = 0

    }

    var mWayBillInfoJson = "{}"
    var mWayBillInfoJObj = JSONObject()
    var mPrintMaxNum = 0

    @SuppressLint("SetTextI18n")
    override fun getWayBillInfoS(data: JSONObject, dataStr: String) {
        waybill_info_tv.text = "运  单 号：${data.optString("billno")}\n货      号：${data.optString("goodsNum")}\n货物名称：${data.optString("product")}\n包      装：${data.optString("packages")}\n发货网点：${data.optString("webidCodeStr")}\n到货网点：${data.optString("ewebidCodeStr")}\n收  货 人：${data.optString("consignee")}\n件      数：${data.optString("qty")}"
        mWayBillInfoJson = dataStr
        mWayBillInfoJObj = data
        mPrintMaxNum = data.optInt("qty", 0)
        end_print_num_ed.setText(mPrintMaxNum.toString())
    }

    override fun getWayBillInfoNull() {
        TalkSureDialog(mContext, getScreenWidth(), "未查询到运单，请确认输入运单号是否有误！").show()
        clearInfo()
    }
}