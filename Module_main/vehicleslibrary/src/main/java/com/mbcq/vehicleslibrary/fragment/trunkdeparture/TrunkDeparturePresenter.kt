package com.mbcq.vehicleslibrary.fragment.trunkdeparture

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-12 15:44:58
 */

class TrunkDeparturePresenter : BasePresenterImpl<TrunkDepartureContract.View>(), TrunkDepartureContract.Presenter {
    override fun getTrunkDeparture(page: Int, selWebidCode: String, startDate: String, endDate: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("page", page)
        mHttpParams.put("limit", 15)
        mHttpParams.put("selWebidCode", selWebidCode)
        mHttpParams.put("startDate", startDate)
        mHttpParams.put("endDate", endDate)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                mView?.getTrunkDepartureS(Gson().fromJson<List<TrunkDepartureBean>>(obj.optString("data"), object : TypeToken<List<TrunkDepartureBean>>() {}.type))
            }

        })
    }

}