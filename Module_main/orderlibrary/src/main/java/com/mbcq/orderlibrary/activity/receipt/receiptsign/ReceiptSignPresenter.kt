package com.mbcq.orderlibrary.activity.receipt.receiptsign

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-07 09:06:21 回单签收
 */

class ReceiptSignPresenter : BasePresenterImpl<ReceiptSignContract.View>(), ReceiptSignContract.Presenter {
    override fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val params = HttpParams()
        params.put("Page", page)
        params.put("Limit", 15)
//        params.put("SelWebidCode", selWebidCode)
//        params.put("startDate", startDate)
//        params.put("endDate", endDate)
        get<String>(ApiInterface.RECEIPT_MANAGEMENT_SIGN_LOAD_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ReceiptSignBean>>() {}.type))
                }
            }

        })
    }

    override fun complete(jsonStr: String) {
        post<String>(ApiInterface.RECEIPT_MANAGEMENT_SIGN_OVER_POST, getRequestBody(jsonStr), object : CallBacks {
            override fun onResult(result: String) {
                mView?.completeS("")
            }

        })
    }

}