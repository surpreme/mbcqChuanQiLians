package com.mbcq.vehicleslibrary.activity.loadingvehicles.trunk

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.loadingvehicles.LoadingVehiclesBean

/**
 * @author: lzy
 * @time: 2021-03-08 11:24:30 干线有计划装车
 */

class TrunkLoadingVehiclesContract {

    interface View : BaseView {
        fun searchScanInfoS(list: List<LoadingVehiclesBean>)
        fun invalidOrderS(position: Int)
        fun saveScanPostS(position: Int)
        fun getScanVehicleListS(list: List<LoadingVehiclesBean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getScanVehicleList(startDate: String, endDate: String,selWebidCode:String)
        //        fun searchShortFeeder(inoneVehicleFlag: String)
        //        fun searchTrunkDeparture(inoneVehicleFlag: String)
        fun searchScanInfo(sendInfo: String)
        fun searchTrunkDeparture(inoneVehicleFlag: String)

        /**
         * 作废
         * @1短驳
         * @2干线
         */
        fun invalidOrder(inoneVehicleFlag: String, id: Int, mType: Int, position: Int)

        /**
         * 发车
         * @1短驳
         * @2干线
         */
        fun saveScanPost(id: Int, inoneVehicleFlag: String, mType: Int, position: Int)

    }
}
