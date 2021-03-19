package com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparturescan

import android.telecom.Call
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-29 15:37:12 fragment_arrival_trunk_departure_scan
 */

class ArrivalTrunkDepartureScanPresenter : BasePresenterImpl<ArrivalTrunkDepartureScanContract.View>(), ArrivalTrunkDepartureScanContract.Presenter {
    override fun getUnLoading(selEwebidCode: String, startDate: String, endDate: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("selEwebidCode", selEwebidCode)
        mHttpParams.put("startDate", startDate)
        mHttpParams.put("endDate", endDate)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOADING_LOCAL_INFO_GET , mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                mView?.getPageS(Gson().fromJson<List<ArrivalTrunkDepartureScanBean>>(obj.optString("data"), object : TypeToken<List<ArrivalTrunkDepartureScanBean>>() {}.type))
            }

        })
    }

    override fun getLoading(selEwebidCode: String, startDate: String, endDate: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("selEwebidCode", selEwebidCode)
        mHttpParams.put("startDate", startDate)
        mHttpParams.put("endDate", endDate)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SCAN_OVER_LOCAL_INFO_GET , mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                mView?.getPageS(Gson().fromJson<List<ArrivalTrunkDepartureScanBean>>(obj.optString("data"), object : TypeToken<List<ArrivalTrunkDepartureScanBean>>() {}.type))
            }

        })
    }

    override fun sureArrivalCar(inoneVehicleFlag: String) {
        val mXObj=JSONObject()
        mXObj.put("inoneVehicleFlag",inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SURE_ARRIVAL_POST,getRequestBody(mXObj),object :CallBacks{
            override fun onResult(result: String) {
                mView?.sureArrivalCarS(result)

            }

        })
    }
}