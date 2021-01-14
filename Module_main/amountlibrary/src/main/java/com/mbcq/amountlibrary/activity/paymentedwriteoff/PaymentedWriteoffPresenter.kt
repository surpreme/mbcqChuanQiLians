package com.mbcq.amountlibrary.activity.paymentedwriteoff

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.amountlibrary.activity.paymentingwriteoff.PaymentingWriteoffBean
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-09 16:11:17 提付核销
 */

class PaymentedWriteoffPresenter : BasePresenterImpl<PaymentedWriteoffContract.View>(), PaymentedWriteoffContract.Presenter {
    override fun getPaymented(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int) {
        val param = HttpParams()
        param.put("Page", page)
        param.put("Limit", 15)
        param.put("SelWebidCode", selWebidCode)
        param.put("startDate", startDate)
        param.put("endDate", endDate)
        when(type){
            0->{
                param.put("HxType", 0)
                param.put("selType", 5)
            }
            1->{
                param.put("HxType", 1)
                param.put("selType", 5)
            }
            2->{
                param.put("HxType", 2)
                param.put("selType", 5)

            }
        }
        get<String>(ApiInterface.PAYMENTED_WRITE_OFF_SERIAL_NUMBER_GET,param,object :CallBacks{
            override fun onResult(result: String) {
                val obj= JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPaymentedS(Gson().fromJson(obj.optString("data"),object: TypeToken<List<PaymentedWriteoffBean>>(){}.type))

                }

            }

        })
    }

}