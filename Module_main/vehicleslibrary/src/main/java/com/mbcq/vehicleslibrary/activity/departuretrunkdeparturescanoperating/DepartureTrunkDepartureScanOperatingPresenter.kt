package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface

/**
 * @author: lzy
 * @time: 2020-11-04 14:30 :21 干线扫描发车
 */

class DepartureTrunkDepartureScanOperatingPresenter : BasePresenterImpl<DepartureTrunkDepartureScanOperatingContract.View>(), DepartureTrunkDepartureScanOperatingContract.Presenter {
    override fun getCarInfo(inoneVehicleFlag: String) {
        val params = HttpParams()
        params.put("InoneVehicleFlag", inoneVehicleFlag)
        get<String>(ApiInterface.DEPARTURE_RECORD_MAIN_LINE_DEPARTURE_SELECT_LOCAL_INFO_POST,params,object :CallBacks{
            override fun onResult(result: String) {

            }

        })
    }

}