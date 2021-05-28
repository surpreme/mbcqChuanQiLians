package com.mbcq.amountlibrary.activity.receiptmonthlytuberculosissales

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.amountlibrary.activity.paymentingwriteoff.PaymentingWriteoffBean
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-13 08:57:10 回单付核销
 */

class ReceiptMonthlyTuberculosisSalesPresenter : BasePresenterImpl<ReceiptMonthlyTuberculosisSalesContract.View>(), ReceiptMonthlyTuberculosisSalesContract.Presenter {
    override fun getMonthly(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int) {
        val param = HttpParams()
        param.put("Page", page)
        param.put("Limit", 15)
        param.put("SelWebidCode", selWebidCode)
        param.put("startDate", startDate)
        param.put("endDate", endDate)
        when (type) {
            0 -> {
                param.put("HxType", 0)
                param.put("selType", 8)
            }
            1 -> {
                param.put("HxType", 1)
                param.put("selType", 8)
            }
            2 -> {
                param.put("HxType", 2)
                param.put("selType", 8)

            }
        }
        get<String>(ApiInterface.RECEIPT_MONTHLY_TUBERCULOSIS_SALES_RECORD_GET, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getMonthlyS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ReceiptMonthlyTuberculosisSalesBean>>() {}.type))

                }

            }

        })
    }

}