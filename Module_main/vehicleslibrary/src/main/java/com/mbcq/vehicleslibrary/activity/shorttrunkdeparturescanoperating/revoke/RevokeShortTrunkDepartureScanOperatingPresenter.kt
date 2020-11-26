package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-10 10:02:03 撤销干线装车扫描
 */

class RevokeShortTrunkDepartureScanOperatingPresenter : BasePresenterImpl<RevokeShortTrunkDepartureScanOperatingContract.View>(), RevokeShortTrunkDepartureScanOperatingContract.Presenter {
    override fun revokeOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String,scanPercentage:String) {
        val jsonO = JSONObject()
        jsonO.put("Billno", billno)
        jsonO.put("LableNo", lableNo)
        jsonO.put("DeviceNo", deviceNo)
        jsonO.put("InOneVehicleFlag", inOneVehicleFlag)
        jsonO.put("scanPercentage", scanPercentage)
//        jsonO.put("ScanType", 0)
//        jsonO.put("ScanTypeStr", "PDA")
        post<String>(ApiInterface.DEPARTURE_SHORT_FEEDER_DEPARTURE_SCAN_INFO_REVOKE_POST, getRequestBody(jsonO), object : CallBacks {
            override fun onResult(result: String) {
                mView?.revokeOrderS(billno,lableNo)

            }

        })
    }
    override fun getCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOCAL_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        mView?.getCarInfoS(Gson().fromJson(dataObj.optString("data"), object : TypeToken<List<RevokeShortTrunkDepartureScanOperatingBean>>() {}.type))
                    }
                }
            }

        })
    }
}