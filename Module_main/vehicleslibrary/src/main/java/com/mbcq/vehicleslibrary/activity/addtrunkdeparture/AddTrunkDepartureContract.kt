package com.mbcq.vehicleslibrary.activity.addtrunkdeparture

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-14 11:02:45
 */

class AddTrunkDepartureContract {

    interface View : BaseView {
        fun getDepartureBatchNumberS(result: String)
        fun getVehicleS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getDepartureBatchNumber()
        fun getVehicles()

    }
}
