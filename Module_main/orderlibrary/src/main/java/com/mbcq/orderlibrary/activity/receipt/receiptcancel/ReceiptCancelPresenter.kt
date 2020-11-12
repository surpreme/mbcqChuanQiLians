package com.mbcq.orderlibrary.activity.receipt.receiptcancel

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-12 15:04:02 回单取消
 */

class ReceiptCancelPresenter : BasePresenterImpl<ReceiptCancelContract.View>(), ReceiptCancelContract.Presenter {
    /**
     *  {
    "cztype": "接收",
    "gsid": 2001,
    "czgs": "test2",
    "czwebid": "汕头",
    "czdate": "2020-11-07T16:34:59",
    "czman": "汕头",
    "id": 43,
    "billno": "10010000019"
    }
    {
    "cztype": "寄出",
    "gsid": 2001,
    "czgs": "test2",
    "czwebid": "1003",
    "czdate": "2020-11-07T00:00:00",
    "czman": "",
    "id": 38,
    "billno": "10010000019"
    }
    {
    "cztype": "签收",
    "gsid": 2001,
    "czgs": "test2",
    "czwebid": "",
    "czdate": "2020-11-07T00:00:00",
    "czman": "汕头",
    "id": 29,
    "billno": "10010000019"
    }
     */
    override fun searchOrder(billno: String) {
        val params = HttpParams()
        params.put("Billno", billno)
        get<String>(ApiInterface.RECEIPT_CANCEL_RECEIVE_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.searchOrderS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ReceiptCancelBean>>() {}.type))

                }

            }

        })
    }

    override fun deleteOrderState(czType: String, id: String, position: Int) {
        val jsonObject = JSONObject()
        jsonObject.put("czType", czType)
        jsonObject.put("id", id)
        post<String>(ApiInterface.RECEIPT_CANCEL_RECEIVE_REMOVE_STATE_GET, getRequestBody(jsonObject), object : CallBacks {
            override fun onResult(result: String) {
                mView?.deleteOrderStateS(position, "")
            }

        })
    }

}