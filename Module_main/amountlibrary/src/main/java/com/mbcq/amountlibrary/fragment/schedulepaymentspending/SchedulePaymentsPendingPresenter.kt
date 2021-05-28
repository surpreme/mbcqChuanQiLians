package com.mbcq.amountlibrary.fragment.schedulepaymentspending

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-20 16:32:45 待发款明细表
 */

class SchedulePaymentsPendingPresenter : BasePresenterImpl<SchedulePaymentsPendingContract.View>(), SchedulePaymentsPendingContract.Presenter {
    override fun getPage(page: Int) {
        val jsonObject = JSONObject()
        jsonObject.put("Page", page)
        jsonObject.put("SelType", 10)
        jsonObject.put("Limit", 15)
        val itemObj=JSONObject()
        itemObj.put("hkIsOut",1)
        jsonObject.put("WaybillState", itemObj)
        post<String>(ApiInterface.GENERAL_SCHEDULE_PAYMENTS_PENDING_INFO_GET, getRequestBody(jsonObject), object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<SchedulePaymentsPendingBean>>() {}.type))
                }

            }

        })
    }

}