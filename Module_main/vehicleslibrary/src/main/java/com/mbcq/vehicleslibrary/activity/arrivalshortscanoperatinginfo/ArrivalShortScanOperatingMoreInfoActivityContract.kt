package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperatinginfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.arrivalscanoperatingmoreinfo.ArrivalScanOperatingMoreInfoBean

/**
 * @author: lzy
 * @time: 2021-03-19 13:43:12 短驳到车扫描详情
 */

class ArrivalShortScanOperatingMoreInfoActivityContract {

    interface View : BaseView {
        fun getScanInfoS(list: List<ArrivalShortScanOperatingMoreInfoBean>)
        fun getScanCarInfoS(list: List<ArrivalShortScanOperatingMoreInfoBean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getScanInfo(billno: String, inoneVehicleFlag: String)
        fun getScanCarInfo(inoneVehicleFlag: String)
    }
}
