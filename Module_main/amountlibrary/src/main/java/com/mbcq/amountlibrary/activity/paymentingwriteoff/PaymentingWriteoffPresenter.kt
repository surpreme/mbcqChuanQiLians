package com.mbcq.amountlibrary.activity.paymentingwriteoff

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-09 15:23:07 现付核销
 */

class PaymentingWriteoffPresenter : BasePresenterImpl<PaymentingWriteoffContract.View>(), PaymentingWriteoffContract.Presenter {
    override fun getPaymenting(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int) {
        val param = HttpParams()
        param.put("Page", page)
        param.put("Limit", 15)
        param.put("SelWebidCode", selWebidCode)
        param.put("startDate", startDate)
        param.put("endDate", endDate)
        when (type) {
            0 -> {
                param.put("HxType", 0)
                param.put("selType", 1)
            }
            1 -> {
                param.put("HxType", 1)
                param.put("selType", 1)
            }
            2 -> {
                param.put("HxType", 2)
                param.put("selType", 1)

            }
        }
        get<String>(ApiInterface.PAYMENTING_WRITE_OFF_RECORD_GET, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPaymentingS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<PaymentingWriteoffBean>>() {}.type))

                }

            }

        })
    }


}