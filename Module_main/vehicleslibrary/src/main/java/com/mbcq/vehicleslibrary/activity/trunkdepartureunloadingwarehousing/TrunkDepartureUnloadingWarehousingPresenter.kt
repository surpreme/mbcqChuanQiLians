package com.mbcq.vehicleslibrary.activity.trunkdepartureunloadingwarehousing

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-31 16:26:03 干线卸车入库
 */

class TrunkDepartureUnloadingWarehousingPresenter : BasePresenterImpl<TrunkDepartureUnloadingWarehousingContract.View>(), TrunkDepartureUnloadingWarehousingContract.Presenter {
    override fun getVehicleInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOCAL_INFO_POST, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        mView?.getVehicleInfoS(Gson().fromJson<List<TrunkDepartureUnloadingWarehousingBean>>(dataObj.optString("data"), object : TypeToken<List<TrunkDepartureUnloadingWarehousingBean>>() {}.type))

                    }

                }
            }

        })
    }

    override fun getVehicleReceiptInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        params.put("type", 1)
        get<String>(ApiInterface.TRUNK_DEPARTURE_UNLOADING_WAREHOUSING_RECEIPT_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        mView?.getVehicleReceiptInfoS(Gson().fromJson<List<TrunkDepartureUnloadingWarehousingBean>>(dataObj.optString("data"), object : TypeToken<List<TrunkDepartureUnloadingWarehousingBean>>() {}.type))

                    }

                }
            }

        })
    }
    override fun confirmCar(data: TrunkDepartureBean, position: Int) {
        val jsonObj = JSONObject()
        jsonObj.put("id", data.id)
        jsonObj.put("inoneVehicleFlag", data.inoneVehicleFlag)
        val jsonArray = JSONArray()
        val itemObj = JSONObject()
        itemObj.put("id", data.id)
        itemObj.put("inoneVehicleFlag", data.inoneVehicleFlag)
        jsonArray.put(itemObj)
        jsonObj.put("GxVehicleDetLst", jsonArray)
        post<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_ARRIVAL_CONFIRM_LOCAL_INFO_POST, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                data.vehicleState = 2
                data.vehicleStateStr = "到货"
                mView?.confirmCarS(data, position)
//                mView?.confirmCarS(position)
            }

        })
    }
    override fun UnloadingWarehousing(commonStr: String, inoneVehicleFlag: String) {
        val jsonObj = JSONObject()
        jsonObj.put("commonStr", commonStr)
        jsonObj.put("inoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.TRUNK_DEPARTURE_UNLOADING_WAREHOUSING_POST, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.UnloadingWarehousingS("车次为$inoneVehicleFlag,运单号为$commonStr")
            }

        })
    }
}