package com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating

import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface

/**
 * @author: lzy
 * @time: 2020-10-30 09:24:35 到车
 */

class ArrivalTrunkDepartureScanOperatingPresenter : BasePresenterImpl<ArrivalTrunkDepartureScanOperatingContract.View>(), ArrivalTrunkDepartureScanOperatingContract.Presenter {
    override fun getCarInfo(inoneVehicleFlag: String) {
        val params=HttpParams()
        params.put("inoneVehicleFlag",inoneVehicleFlag)
//        get<String>(ApiInterface.)
    }

}