package com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan.arrivalvehiclestrunkscan

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan.arrivalvehiclesshortscan.ArrivalVehiclesScanShortSearchWithBillnoBean

/**
 * @author: lzy
 * @time: 2021-03-20 09:51:43 干线到车扫描记录
 */

class ArrivalVehiclesScanShortTrunkContract {

    interface View : BaseView {
        fun getTrunkCarNumberS(result: ArrivalVehiclesScanTrunkSearchWithBillnoBean)

    }

    interface Presenter : BasePresenter<View> {
        fun getTrunkCarNumber(billno: String)
    }
}
