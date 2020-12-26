package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperatingmoreinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperatingmoreinfo.ShortTrunkDepartureScanOperatingMoreCarInfoBean
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperatingmoreinfo.ShortTrunkDepartureScanOperatingMoreInfoBean

/**
 * @author: lzy
 * @time: 2020-12-14 13:31:14 干线扫描详情
 */

class DepartureTrunkDepartureScanOperatingScanInfoContract {

    interface View : BaseView {
        fun getPageDataS(list: List<DepartureTrunkDepartureScanOperatingScanInfoBean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getPageData(billno: String, inOneVehicleFlag: String, scanOpeType: Int)

        //        fun getCarScanData(inOneVehicleFlag: String, scanOpeType: Int)
        fun getCarInfo(inoneVehicleFlag: String)

    }
}
