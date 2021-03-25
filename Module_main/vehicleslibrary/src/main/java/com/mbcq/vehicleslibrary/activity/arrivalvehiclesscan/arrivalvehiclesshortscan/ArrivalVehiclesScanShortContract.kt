package com.mbcq.vehicleslibrary.activity.arrivalvehiclesscan.arrivalvehiclesshortscan

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2021-03-20 09:33:16
 */

class ArrivalVehiclesScanShortContract {

    interface View : BaseView {
        fun getShortCarNumberS(result: ArrivalVehiclesScanShortSearchWithBillnoBean)

    }

    interface Presenter : BasePresenter<View> {
        fun getShortCarNumber(billno: String)
    }
}
