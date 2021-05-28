package com.mbcq.orderlibrary.activity.acceptbillingrecording

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-17 09:58:12  改单申请 记录
 */

class AcceptBillingRecordingPresenter : BasePresenterImpl<AcceptBillingRecordingContract.View>(), AcceptBillingRecordingContract.Presenter {
    /**
     * {"code":0,"msg":"","count":16,"data":[
    {
    "companyId": 2001,
    "opeWebidCode": 1003,
    "opeWebidCodeStr": "汕头",
    "billno": "38600001103",
    "updateRemark": "",
    "updateContent": "等通知放货=33改为0；",
    "updateItem": "等通知放货-",
    "isUpdCon": 1,
    "isUpdMon": 0,
    "opeDate": "2019-01-10T15:57:12",
    "opeMan": "汕头",
    "yyCheckMan": "",
    "yyCheckDate": "2019-01-11T17:00:02",
    "cwCheckMan": null,
    "cwCheckDate": null,
    "cwCheWebidCode": null,
    "cwCheWebidCodeStr": null,
    "cancelMan": "汕头",
    "cancelDate": "2020-09-02T18:15:01",
    "cancelWebidCode": 1003,
    "cancelWebidCodeStr": "汕头",
    "cancelRemark": "测试",
    "fromType": 0,
    "fromTypeStr": null
    }
    ]}
     */
    override fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String, type: String) {
        val param = HttpParams()
        param.put("Page", page)
        param.put("Limit", 15)
        param.put("SelWebidCode", selWebidCode)
        param.put("startDate", startDate)
        param.put("endDate", endDate)
        if (type.isNotBlank())
            param.put("CommonStr", type)
        get<String>(ApiInterface.FIXED_WAYBILL_RECORD_GET, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<AcceptBillingRecordingBean>>() {}.type))

                }

            }

        })
    }

    override fun rejectOrder(billno: String, id: String, position: Int) {
        val jsonObj = JSONObject()
        jsonObj.put("billno", billno)
        jsonObj.put("id", id)
        post<String>(ApiInterface.ACCEPT_BILLING_RECORDING_REJECT_ORDER_POST, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.rejectOrderS(result, position)
            }

        })
    }

    override fun searchOrder(billno: String) {
        val param = HttpParams()
        param.put("billno", billno)
        get<String>(ApiInterface.FIXED_WAYBILL_RECORD_GET, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.searchOrderS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<AcceptBillingRecordingBean>>() {}.type))

                }

            }

        })
    }

}