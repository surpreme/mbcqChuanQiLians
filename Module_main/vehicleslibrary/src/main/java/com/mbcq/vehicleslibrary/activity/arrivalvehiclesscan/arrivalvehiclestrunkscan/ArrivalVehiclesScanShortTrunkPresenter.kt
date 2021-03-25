package com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan.arrivalvehiclestrunkscan

import com.google.gson.Gson
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface
import com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan.arrivalvehiclesshortscan.ArrivalVehiclesScanShortSearchWithBillnoBean

/**
 * @author: lzy
 * @time: 2021-03-20 09:51:43 干线到车扫描记录
 */

class ArrivalVehiclesScanShortTrunkPresenter : BasePresenterImpl<ArrivalVehiclesScanShortTrunkContract.View>(), ArrivalVehiclesScanShortTrunkContract.Presenter {
    override fun getTrunkCarNumber(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_BILLNO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                mView?.getTrunkCarNumberS(Gson().fromJson(result, ArrivalVehiclesScanTrunkSearchWithBillnoBean::class.java))
            }

        })
    }

}