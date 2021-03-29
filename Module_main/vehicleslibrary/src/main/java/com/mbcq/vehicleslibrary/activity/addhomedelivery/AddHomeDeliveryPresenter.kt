package com.mbcq.vehicleslibrary.activity.addhomedelivery

import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener

/**
 * @author: lzy
 * @time: 2021-01-14 17:09:34 添加上门提货配置
 */

class AddHomeDeliveryPresenter : BasePresenterImpl<AddHomeDeliveryContract.View>(), AddHomeDeliveryContract.Presenter {
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