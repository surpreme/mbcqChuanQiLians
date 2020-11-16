package com.mbcq.vehicleslibrary.activity.loadingvehicles

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-04 09:49:32 装车
 */

class LoadingVehiclesPresenter : BasePresenterImpl<LoadingVehiclesContract.View>(), LoadingVehiclesContract.Presenter {
    override fun getShortFeeder() {
        val params = HttpParams()
        params.put("page", 1)
        params.put("limit", 1000)
        params.put("vehicleState", 0)//发车计划中
//        params.put("VehicleStateStr", 0)//发车计划中
//        params.put("IsScan", 1)//是否扫描
        params.put("CommonStr", "1,2")//是否扫描
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val list = Gson().fromJson<List<LoadingVehiclesBean>>(obj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                    val data = mutableListOf<LoadingVehiclesBean>()
                    for (item in list) {
                        item.type = 0
                        data.add(item)
                    }
                    mView?.getShortFeederS(data, false)
                }

            }

        })
    }

    override fun searchShortFeeder(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val list = Gson().fromJson<List<LoadingVehiclesBean>>(obj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                    val data = mutableListOf<LoadingVehiclesBean>()
                    for (item in list) {
                        item.type = 0
                        data.add(item)
                    }
                    if (data.isEmpty()) {
                        searchTrunkDeparture(inoneVehicleFlag)
                    } else
                        mView?.getShortFeederS(data, true)
                }

            }

        })
    }

    override fun getTrunkDeparture() {
        val mHttpParams = HttpParams()
        mHttpParams.put("page", 1)
        mHttpParams.put("limit", 1000)
        mHttpParams.put("vehicleState", 0)//发车计划中
        mHttpParams.put("CommonStr", 0)//发车计划中
        mHttpParams.put("VehicleStateStr", 0)//发车计划中
        mHttpParams.put("IsScan", 1)//是否扫描
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val list = Gson().fromJson<List<LoadingVehiclesBean>>(obj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                val data = mutableListOf<LoadingVehiclesBean>()
                for (item in list) {
                    item.type = 1
                    data.add(item)
                }
                mView?.getTrunkDepartureS(data, false)
            }

        })
    }

    override fun searchTrunkDeparture(inoneVehicleFlag: String) {
        val mHttpParams = HttpParams()
        mHttpParams.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, mHttpParams, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val list = Gson().fromJson<List<LoadingVehiclesBean>>(obj.optString("data"), object : TypeToken<List<LoadingVehiclesBean>>() {}.type)
                val data = mutableListOf<LoadingVehiclesBean>()
                for (item in list) {
                    item.type = 1
                    data.add(item)
                }
                mView?.getTrunkDepartureS(data, true)
            }

        })
    }

}