package com.mbcq.orderlibrary.activity.receipt.receiptconsignment

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.activity.receipt.receiptsign.ReceiptSignBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-10 18:11:43 回单寄出
 */

class ReceiptConsignmentPresenter : BasePresenterImpl<ReceiptConsignmentContract.View>(), ReceiptConsignmentContract.Presenter {
    override fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val params = HttpParams()
        params.put("SelType",6)//6按票7按批次
        params.put("Page",page)
        params.put("Limit",15)
//        params.put("SelWebidCode", selWebidCode)
//        params.put("startDate", startDate)
//        params.put("endDate", endDate)
        get<String>(ApiInterface.RECEIPT_MANAGEMENT_CONSIGNMENT_LOAD_GET , params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ReceiptConsignmentBean>>() {}.type))
                }
            }

        })
    }

    override fun complete(jsonStr: String) {
        post<String>(ApiInterface.RECEIPT_MANAGEMENT_CONSIGNMENT_OVER_POST, getRequestBody(jsonStr,false), object : CallBacks {
            override fun onResult(result: String) {
                mView?.completeS("")


            }
        })
    }

}