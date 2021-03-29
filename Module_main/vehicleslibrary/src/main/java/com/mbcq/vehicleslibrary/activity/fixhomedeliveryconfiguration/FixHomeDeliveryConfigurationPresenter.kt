package com.mbcq.vehicleslibrary.activity.fixhomedeliveryconfiguration

import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2021-03-27 17:53:06 修改上门提货配置
 */

class FixHomeDeliveryConfigurationPresenter : BasePresenterImpl<FixHomeDeliveryConfigurationContract.View>(), FixHomeDeliveryConfigurationContract.Presenter {
    /**
     * {"code":0,"msg":"","count":1,"data":[
    {
    "proCode": 0,
    "inOneVehicleFlag": "TH1003-20210315-003"
    }
    ]}
     */
    override fun getBatch() {
        get<String>(ApiInterface.HOME_DELIVERY_BATCH_GET+"?=", null, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    if (it.isNull(0))return
                    val childrenObj=it.getJSONObject(0)
                    mView?.getBatchS(childrenObj.optString("inOneVehicleFlag"))
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
}