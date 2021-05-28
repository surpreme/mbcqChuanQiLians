package com.mbcq.amountlibrary.fragment.paymentconfirmation

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-20 16:29:12 贷款回款确认
 */

class PaymentConfirmationPresenter : BasePresenterImpl<PaymentConfirmationContract.View>(), PaymentConfirmationContract.Presenter {
    override fun getPage(page: Int) {
        val params = HttpParams()
//        params.put("page", page)
//        params.put("SelType",8)
//        params.put("Limit",10)
        get<String>(ApiInterface.GENERAL_PAYMENT_CONFIRMATION_INFO_GET+"?=",params,object :CallBacks{
            override fun onResult(result: String) {
                val obj=JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"),object:TypeToken<List<PaymentConfirmationBean>>(){}.type))

                }
            }

        })
    }

}