package com.mbcq.amountlibrary.activity.monthlytuberculosissales

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.amountlibrary.activity.paymentedwriteoff.PaymentedWriteoffBean
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-13 10:33:09 月结核销
 */

class MonthlyTuberculosisSalesPresenter : BasePresenterImpl<MonthlyTuberculosisSalesContract.View>(), MonthlyTuberculosisSalesContract.Presenter {
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
                param.put("selType", 11)
            }
            1 -> {
                param.put("HxType", 1)
                param.put("selType", 11)
            }
            2 -> {
                param.put("HxType", 2)
                param.put("selType", 11)

            }
        }
        get<String>(ApiInterface.MONTHLY_TUBERCULOSIS_SALES_RECORD_GET, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getMonthlyS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<MonthlyTuberculosisSalesBean>>() {}.type))

                }

            }

        })


    }

}