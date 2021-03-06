package com.mbcq.vehicleslibrary.fragment.trunkdeparture

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
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
                mView?.getTrunkDepartureS(Gson().fromJson<List<TrunkDepartureBean>>(obj.optString("data"), object : TypeToken<List<TrunkDepartureBean>>() {}.type), Gson().fromJson(obj.optString("totalRow"), TrunkDepartureTotalBean::class.java))
            }

        })
    }

    override fun invalidOrder(inoneVehicleFlag: String, id: Int) {
        val obj = JsonObject()
        obj.addProperty("inoneVehicleFlag", inoneVehicleFlag)
        obj.addProperty("id", id)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_INVALID_INFO_POST, getRequestBody(obj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.invalidOrderS()

            }

        })

    }

    override fun searchInoneVehicleFlagTrunkDeparture(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    val list = Gson().fromJson<List<TrunkDepartureBean>>(obj.optString("data"), object : TypeToken<List<TrunkDepartureBean>>() {}.type)
                    if (list.isNotEmpty()) {
                        mView?.getTrunkDepartureS(list, Gson().fromJson(obj.optString("totalRow"), TrunkDepartureTotalBean::class.java))

                    }
                }
            }


        })
    }

    override fun searchScanInfo(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_BILLNO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)

                obj.optJSONArray("data")?.let {
                    for (index in 0..it.length()) {
                        if (!it.isNull(index)) {
                            val itemObj = it.getJSONObject(index)
                            searchInoneVehicleFlagTrunkDeparture(itemObj.optString("inoneVehicleFlag"))
                        }
                    }

                }
            }

        })
    }

}