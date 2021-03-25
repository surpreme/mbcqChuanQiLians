package com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan.arrivalvehiclesshortscan

import com.google.gson.Gson
import com.lzy.okgo.model.HttpParams
import com.mbcq.baselibrary.ui.mvp.BasePresenterImpl
import com.mbcq.commonlibrary.ApiInterface

/**
 * @author: lzy
 * @time: 2021-03-20 09:33:16
 */

class ArrivalVehiclesScanShortPresenter : BasePresenterImpl<ArrivalVehiclesScanShortContract.View>(), ArrivalVehiclesScanShortContract.Presenter {
    override fun getShortCarNumber(billno: String) {
        val params = HttpParams()
        params.put("billno", billno)
        get<String>(ApiInterface.DEPARTURE_RECORD_SHORT_FEEDER_SELECT_BILLNO_GET, params, object : CallBacks {
            override fun onResult(result: String) {
                mView?.getShortCarNumberS(Gson().fromJson(result, ArrivalVehiclesScanShortSearchWithBillnoBean::class.java))
            }

        })
    }

}