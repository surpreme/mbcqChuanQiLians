package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-04 16:05:23 短驳干线发车
 */

class ShortTrunkDepartureScanOperatingPresenter : BasePresenterImpl<ShortTrunkDepartureScanOperatingContract.View>(), ShortTrunkDepartureScanOperatingContract.Presenter {
    override fun getCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_SELECT_LOCAL_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(1)) {
                        val dataObj = it.optJSONObject(1)
                        mView?.getCarInfoS(Gson().fromJson(dataObj.optString("data"), object : TypeToken<List<ShortTrunkDepartureScanOperatingBean>>() {}.type))

                    }

                }
            }

        })
    }

    /**
     * {
      "Billno": "10030002659",
      "LableNo": "100300026590003",
      "DeviceNo": "123",
      "InOneVehicleFlag": "DB1003-20201105-006",
      "ScanType": 0,
      "ScanTypeStr": "PDA"
    }
    {"code":0,"ljCode":1,"msg":"标签号已扫描，不能重复扫描"}
    {"code":0,"ljCode":0,"msg":""}
     */
    override fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String) {
        val jsonO = JSONObject()
        jsonO.put("Billno", billno)
        jsonO.put("LableNo", lableNo)
        jsonO.put("DeviceNo", deviceNo)
        jsonO.put("InOneVehicleFlag", inOneVehicleFlag)
        jsonO.put("ScanType", 0)
        jsonO.put("ScanTypeStr", "PDA")
        post<String>(ApiInterface.DEPARTURE_SHORT_FEEDER_DEPARTURE_SCAN_INFO_POST, getRequestBody(jsonO), object : CallBacks {
            override fun onResult(result: String) {
                mView?.scanOrderS(billno, soundStr)

            }

        })
    }

    override fun saveScanPost(id: Int, inoneVehicleFlag: String) {
        val postBody = JsonObject()
        postBody.addProperty("id", id)
        postBody.addProperty("InoneVehicleFlag", inoneVehicleFlag)
        post<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_DEPARTURE_COMPLETE_LOCAL_INFO_POST, getRequestBody(postBody), object : CallBacks {
            override fun onResult(result: String) {
                mView?.saveScanPostS("")

            }

        })
    }

}