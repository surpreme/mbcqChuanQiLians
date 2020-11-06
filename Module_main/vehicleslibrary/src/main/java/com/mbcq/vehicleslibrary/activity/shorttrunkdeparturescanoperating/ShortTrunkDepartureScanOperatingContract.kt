package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-04 16:05:23 短驳干线发车
 */

class ShortTrunkDepartureScanOperatingContract {

    interface View : BaseView {
        fun getCarInfoS(list: List<ShortTrunkDepartureScanOperatingBean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getCarInfo(inoneVehicleFlag: String)

    }
}