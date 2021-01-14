package com.mbcq.orderlibrary.activity.homedelivery

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.activity.acceptbillingrecording.AcceptBillingRecordingBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-14 15:44:03 上门提货
 */

class HomeDeliveryPresenter : BasePresenterImpl<HomeDeliveryContract.View>(), HomeDeliveryContract.Presenter {
    override fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val param = HttpParams()
        param.put("Page", page)
        param.put("Limit", 15)
        param.put("SelWebidCode", selWebidCode)
        param.put("startDate", startDate)
        param.put("endDate", endDate)
        get<String>(ApiInterface.HOME_DELIVERY_RECORD_GET, param, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getPageS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<HomeDeliveryBean>>() {}.type))

                }

            }

        })
    }

}