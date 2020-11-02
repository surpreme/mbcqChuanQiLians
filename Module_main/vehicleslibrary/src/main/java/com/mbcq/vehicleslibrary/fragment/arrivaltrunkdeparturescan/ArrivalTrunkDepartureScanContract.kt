package com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparturescan

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean

/**
 * @author: lzy
 * @time: 2020-10-29 15:37:12 fragment_arrival_trunk_departure_scan
 */

class ArrivalTrunkDepartureScanContract {

    interface View : BaseView {
        fun getPageS(list: List<ArrivalTrunkDepartureScanBean>)

    }

    interface Presenter : BasePresenter<View> {
       /*
        * 获取未到车 这个为到货网点编号
        */
        fun getUnLoading(selEwebidCode: String, startDate: String, endDate: String)
        /**
         * 获取已到车 这个为到货网点编号
         */
        fun getLoading(selEwebidCode: String, startDate: String, endDate: String)
    }
}
