package com.mbcq.orderlibrary.activity.receipt.receiptreceive

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.activity.receipt.receiptconsignment.ReceiptConsignmentBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-11 08:58:16 回单接收
 */

class ReceiptReceivePresenter : BasePresenterImpl<ReceiptReceiveContract.View>(), ReceiptReceiveContract.Presenter {
    override fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val params = HttpParams()
        params.put("SelType", 6)//6按票7按批次
        params.put("Page", page)
        params.put("Limit", 15)
//        params.put("SelWebidCode", selWebidCode)
//        params.put("startDate", startDate)
//        params.put("endDate", endDate)
        get<String>(ApiInterface.RECEIPT_MANAGEMENT_RECEIVE_LOAD_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ReceiptReceiveBean>>() {}.type))
                }
            }

        })
    }

    override fun complete(jsonStr: String) {
        post<String>(ApiInterface.RECEIPT_MANAGEMENT_RECEIVE_OVER_POST, getRequestBody(jsonStr), object : CallBacks {
            override fun onResult(result: String) {
                mView?.completeS("")

            }

        })
    }

}