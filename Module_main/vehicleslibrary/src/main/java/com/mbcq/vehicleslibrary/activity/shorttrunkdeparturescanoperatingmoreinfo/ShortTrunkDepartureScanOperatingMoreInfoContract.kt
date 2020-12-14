package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperatingmoreinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingBean

/**
 * @author: lzy
 * @time: 2020-12-14 08:38:10  短驳扫描详情
 */

class ShortTrunkDepartureScanOperatingMoreInfoContract {

    interface View : BaseView {
        fun getPageDataS(list: List<ShortTrunkDepartureScanOperatingMoreInfoBean>)
        fun getCarScanDataS(list: List<ShortTrunkDepartureScanOperatingMoreInfoBean>)
        fun getCarInfoS(list: List<ShortTrunkDepartureScanOperatingMoreCarInfoBean>,mScanList: List<ShortTrunkDepartureScanOperatingMoreInfoBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPageData(billno: String, inOneVehicleFlag: String, scanOpeType: Int)
        fun getCarInfo(inoneVehicleFlag: String,list: List<ShortTrunkDepartureScanOperatingMoreInfoBean>)
        fun getCarScanData(inOneVehicleFlag: String, scanOpeType: Int)
    }
}
