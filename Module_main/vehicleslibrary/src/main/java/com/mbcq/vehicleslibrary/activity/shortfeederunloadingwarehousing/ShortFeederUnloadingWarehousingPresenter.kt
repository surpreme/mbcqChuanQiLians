package com.mbcq.vehicleslibrary.activity.shortfeederunloadingwarehousing

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.fixedscanshortfeederconfiguration.FixedScanShortFeederConfigurationBean
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean
import org.json.JSONArray
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-31 16:28:43 短驳卸车入库
 */

class ShortFeederUnloadingWarehousingPresenter : BasePresenterImpl<ShortFeederUnloadingWarehousingContract.View>(), ShortFeederUnloadingWarehousingContract.Presenter {
    override fun getVehicleInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOCAL_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        mView?.getVehicleInfoS(Gson().fromJson<List<ShortFeederUnloadingWarehousingBean>>(dataObj.optString("data"), object : TypeToken<List<ShortFeederUnloadingWarehousingBean>>() {}.type))

                    }

                }
            }

        })
    }

    override fun getVehicleReceiptInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        params.put("type", 1)
        get<String>(ApiInterface.SHORT_FEEDER_UNLOADING_WAREHOUSING_RECEIPT_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        mView?.getVehicleReceiptInfoS(Gson().fromJson<List<ShortFeederUnloadingWarehousingBean>>(dataObj.optString("data"), object : TypeToken<List<ShortFeederUnloadingWarehousingBean>>() {}.type))

                    }

                }
            }

        })
    }
    override fun confirmCar(data: ShortFeederBean, position: Int) {
        val jsonObj = JSONObject()
        jsonObj.put("Id", data.id)
        jsonObj.put("InoneVehicleFlag", data.inoneVehicleFlag)
        val jsonArray = JSONArray()
        val itemObj = JSONObject()
        itemObj.put("Id", data.id)
        itemObj.put("InoneVehicleFlag", data.inoneVehicleFlag)
        jsonArray.put(itemObj)
        jsonObj.put("DbVehicleDetLst", jsonArray)
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_ARRIVAL_CONFIRM_LOCAL_INFO_POST, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.confirmCarS(data, position)
            }

        })

    }

    override fun UnloadingWarehousing(commonStr: String, inoneVehicleFlag: String) {
        val jsonObj = JSONObject()
        jsonObj.put("commonStr", commonStr)
        jsonObj.put("inoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.SHORT_FEEDER_UNLOADING_WAREHOUSING_POST, getRequestBody(jsonObj), object : CallBacks {
            override fun onResult(result: String) {
                mView?.UnloadingWarehousingS("车次为$inoneVehicleFlag,运单号为$commonStr")
            }

        })
    }

}