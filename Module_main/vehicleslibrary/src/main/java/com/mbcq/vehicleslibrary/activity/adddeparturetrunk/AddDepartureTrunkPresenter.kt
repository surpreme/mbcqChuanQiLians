package com.mbcq.vehicleslibrary.activity.adddeparturetrunk

import com.mbcq.baselibrary.gson.GsonUtils
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2020-11-19 10:14:45 添加无计划干线扫描
 */

class AddDepartureTrunkPresenter : BasePresenterImpl<AddDepartureTrunkContract.View>(), AddDepartureTrunkContract.Presenter {
    /**
     * {"code":0,"msg":"","count":1,"data":[
    {
    "proCode": 0,
    "inOneVehicleFlag": "GX1003-20200917-001"
    }
    ]}
     */
    override fun getDepartureBatchNumber() {
        get<String>(ApiInterface.ADD_TRUNK_TRANSFER_DEPARTURE_BATCH_NUMBER_GET + "?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                val json = JSONTokener(obj.optString("data")).nextValue()
                if (json is JSONArray) {
                    obj.optJSONArray("data")?.let {
                        if (!it.isNull(0)) {
                            val dataObj = it.optJSONObject(0)
                            mView?.getDepartureBatchNumberS(dataObj.optString("inOneVehicleFlag"))
                        }
                    }

                }


            }

        })
    }
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
    override fun saveInfo(ob: JSONObject) {
        post<String>(ApiInterface.COMPELETE_TRUNK_TRANSFER_DEPARTURE_BATCH_NUMBER_POST, getRequestBody(ob), object : CallBacks {
            override fun onResult(result: String) {
                mView?.saveInfoS(GsonUtils.toPrettyFormat(ob))

            }

        })
    }
}