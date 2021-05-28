package com.mbcq.amountlibrary.activity.allwriteoffpayment.commonwriteoff

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-13 13:45:02 核销记录共用页面
 */

class CommonWriteOffPresenter : BasePresenterImpl<CommonWriteOffContract.View>(), CommonWriteOffContract.Presenter {
    override fun getPaymented(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int, mTitle: String) {
        val param = HttpParams()
        param.put("Page", page)
        param.put("Limit", 15)
        param.put("SelWebidCode", selWebidCode)
        param.put("startDate", startDate)
        param.put("endDate", endDate)
        param.put("HxType", type)
        param.put("selType",
                when {
                    mTitle.contains("提付核销") -> 5
                    mTitle.contains("现付核销") -> 1
                    mTitle.contains("回单付核销") -> 8
                    mTitle.contains("月结核销") -> 11


                    mTitle.contains("干线车费核销") -> 18
                    mTitle.contains("返款核销") -> 14
                    mTitle.contains("中转费核销") -> 16
                    mTitle.contains("短驳车费核销") -> 17
                    else -> 0
                })


        get<String>(
                when {
                    mTitle.contains("提付核销") -> ApiInterface.PAYMENTED_WRITE_OFF_SERIAL_NUMBER_GET
                    mTitle.contains("现付核销") -> ApiInterface.PAYMENTING_WRITE_OFF_RECORD_GET
                    mTitle.contains("回单付核销") -> ApiInterface.RECEIPT_MONTHLY_TUBERCULOSIS_SALES_RECORD_GET
                    mTitle.contains("月结核销") -> ApiInterface.MONTHLY_TUBERCULOSIS_SALES_RECORD_GET


                    mTitle.contains("干线车费核销") -> ApiInterface.MAIN_LINE_FARE_RECORD_GET
                    mTitle.contains("返款核销") -> ApiInterface.REBATE_VERIFICATION_RECORD_GET
                    mTitle.contains("中转费核销") -> ApiInterface.TRANSFER_FEE_VERIFICATION_RECORD_GET
                    mTitle.contains("短驳车费核销") -> ApiInterface.SHUTTLE_FARE_VERIFICATION_RECORD_GET
                    else -> ""
                }, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPaymentedS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<CommonWriteOffBean>>() {}.type))

                }

            }

        })
    }

}