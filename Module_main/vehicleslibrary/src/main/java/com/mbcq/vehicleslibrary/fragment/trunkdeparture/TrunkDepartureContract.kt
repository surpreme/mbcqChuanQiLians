package com.mbcq.vehicleslibrary.fragment.trunkdeparture

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean

/**
 * @author: lzy
 * @time: 2020-09-12 15:44:58
 */

class TrunkDepartureContract {

    interface View : BaseView {
        fun getTrunkDepartureS(list: List<TrunkDepartureBean>)

        fun invalidOrderS()
    }

    interface Presenter : BasePresenter<View> {
        fun getTrunkDeparture(page: Int, selWebidCode: String, startDate: String, endDate: String)
        fun invalidOrder(inoneVehicleFlag: String, id: Int)

    }
}
