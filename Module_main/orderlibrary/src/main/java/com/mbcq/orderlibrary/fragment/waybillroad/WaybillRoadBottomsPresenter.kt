package com.mbcq.orderlibrary.fragment.waybillroad

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.orderlibrary.activity.acceptbilling.AcceptBankInfoBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class WaybillRoadBottomsPresenter : BasePresenterImpl<WaybillRoadBottomsContract.View>(), WaybillRoadBottomsContract.Presenter {
    override fun getTrackRoad(billno: String) {
        val params=HttpParams()
        params.put("Billno",billno)
        get<String>(ApiInterface.TRACK_ROAD_WAYBILL_GET,params,object :CallBacks{
            override fun onResult(result: String) {
                val obj=JSONObject()
                obj.optJSONArray("data")?.let {
                    mView?.getTrackRoadS( Gson().fromJson<List<WaybillRoadBottomBean>>(obj.optString("data"), object : TypeToken<List<WaybillRoadBottomBean>>() {}.type))

                }

            }

        })
    }

}