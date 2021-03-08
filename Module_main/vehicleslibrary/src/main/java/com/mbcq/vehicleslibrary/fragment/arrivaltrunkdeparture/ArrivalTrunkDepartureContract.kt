package com.mbcq.vehicleslibrary.fragment.arrivaltrunkdeparture

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean
import com.mbcq.vehicleslibrary.fragment.trunkdeparture.TrunkDepartureBean

/**
 * @author: lzy
 * @time: 2020-09-21 10:40:36
 */

class ArrivalTrunkDepartureContract {

    interface View : BaseView {
        fun getPageS(list: List<TrunkDepartureBean>, totalNum: Int)
        fun confirmCarS(data: TrunkDepartureBean, position: Int)
        fun canCelCarS(data: TrunkDepartureBean, position: Int)

    }

    interface Presenter : BasePresenter<View> {
        /**
         *  这个为到货网点编号
         */
        fun getArrivalCar(page: Int, selEwebidCode: String, startDate: String, endDate: String)

        /**
         * 确认到车
         */
        fun confirmCar(data: TrunkDepartureBean, position: Int)
        fun canCelCar(data: TrunkDepartureBean, position: Int)
        fun searchScanInfo(billno: String)
        fun searchInoneVehicleFlagTrunkDepature(inoneVehicleFlag: String)
    }
}
