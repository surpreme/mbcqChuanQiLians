package com.mbcq.vehicleslibrary.fragment.arrivalshortfeeder

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean

/**
 * @author: lzy
 * @time: 2020-09-21 09:06
 */

class ArrivalShortFeederContract {

    interface View : BaseView {
        fun getPageS(list: List<ShortFeederBean>,totalNum:Int)
        fun confirmCarS(data: ShortFeederBean, position: Int)

        //        fun confirmCarS( position: Int)
        fun canCelCarS(data: ShortFeederBean, position: Int)
    }

    interface Presenter : BasePresenter<View> {
        //        fun getUnLoading(selEwebidCode: String, startDate: String, endDate: String)
        fun getArrivalCarList(selEwebidCode: String, startDate: String, endDate: String)

        /**
         * 确认到车
         */
        fun confirmCar(data: ShortFeederBean, position: Int)
        fun canCelCar(data: ShortFeederBean, position: Int)
        fun searchScanInfo(billno: String)
        fun searchInoneVehicleFlagShortFeeder(inoneVehicleFlag: String)
    }
}
