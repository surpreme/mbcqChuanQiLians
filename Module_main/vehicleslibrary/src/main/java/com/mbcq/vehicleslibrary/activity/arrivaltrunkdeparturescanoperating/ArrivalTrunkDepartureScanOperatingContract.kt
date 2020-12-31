package com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-10-30 09:24:35 到车
 */

class ArrivalTrunkDepartureScanOperatingContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {
        fun getCarInfo(inoneVehicleFlag: String)

    }
}
