package com.mbcq.vehicleslibrary.activity.loadingvehicles

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-04 09:49:32 装车
 */

class LoadingVehiclesContract {

    interface View : BaseView {
        fun getShortFeederS(list: List<LoadingVehiclesBean>, isScan: Boolean)
        fun getTrunkDepartureS(list: List<LoadingVehiclesBean>, isScan: Boolean)
    }

    interface Presenter : BasePresenter<View> {
        fun getShortFeeder()
        fun searchShortFeeder(inoneVehicleFlag: String)
        fun getTrunkDeparture()
        fun searchTrunkDeparture(inoneVehicleFlag: String)
    }
}
