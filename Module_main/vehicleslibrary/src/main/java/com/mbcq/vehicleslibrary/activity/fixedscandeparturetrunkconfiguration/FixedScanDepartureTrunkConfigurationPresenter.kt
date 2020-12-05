package com.mbcq.vehicleslibrary.activity.fixedscandeparturetrunkconfiguration

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-12-04 15:25:43 修改扫描干线配置
 */

class FixedScanDepartureTrunkConfigurationPresenter : BasePresenterImpl<FixedScanDepartureTrunkConfigurationContract.View>(), FixedScanDepartureTrunkConfigurationContract.Presenter {
    override fun getVehicles() {
        get<String>(ApiInterface.VEHICLE_SELECT_INFO_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val json = JSONTokener(obj.optString("data")).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data")?.let {
                        if (!it.isNull(0)) {
                            mView?.getVehicleS(obj.optString("data"))
                        }
                    }

                }


            }

        })
    }
    override fun getCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOCAL_INFO_POST, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (!it.isNull(0)) {
                        val dataObj = it.optJSONObject(0)
                        mView?.getCarInfoS(Gson().fromJson<List<FixedScanDepartureTrunkConfigurationBean>>(dataObj.optString("data"), object : TypeToken<List<FixedScanDepartureTrunkConfigurationBean>>() {}.type)[0])

                    }

                }
            }

        })
    }
    override fun geSelectVehicles(vehicleNo: String,chaufferMb: String) {
        get<String>(ApiInterface.VEHICLE_SELECT_INFO_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val json = JSONTokener(obj.optString("data")).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data")?.let {
                        if (!it.isNull(0)) {
                            mView?.geSelectVehicleS(obj.optString("data"),vehicleNo,chaufferMb)
                        }
                    }

                }


            }

        })
    }

    override fun saveInfo(postJson: String) {
        post<String>(ApiInterface.SCAN_DEPARTURE_TRUNK_CONFIGURATION_FIXED_GET,getRequestBody(postJson),object :CallBacks{
            override fun onResult(result: String) {
                mView?.saveInfoS(result)
            }

        })

    }
}