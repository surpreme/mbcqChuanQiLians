package com.mbcq.vehicleslibrary.activity.arrivalscanoperatingmoreinfo

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-03-16 14:22:43 到车扫描详情
 */

class ArrivalScanOperatingMoreInfoPresenter : BasePresenterImpl<ArrivalScanOperatingMoreInfoContract.View>(), ArrivalScanOperatingMoreInfoContract.Presenter {
    override fun getScanInfo(billno: String, inoneVehicleFlag: String) {
        val params = HttpParams()
        if (billno.isNotBlank())
            params.put("billno", billno)
        params.put("inoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_VEHICLE_SCANED_ORDER_INFO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                val obj = JSONObject(result)
                obj.optJSONArray("data")?.let {
                    mView?.getScanInfoS(Gson().fromJson(obj.optString("data"), object : TypeToken<List<ArrivalScanOperatingMoreInfoBean>>() {}.type))
                }
            }

        })
    }

}