package com.mbcq.vehicleslibrary.activity.arrivalscanoperatingmoreinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2021-03-16 14:22:43 到车扫描详情
 */

class ArrivalScanOperatingMoreInfoContract {

    interface View : BaseView {
        fun getScanInfoS(list: List<ArrivalScanOperatingMoreInfoBean>, sendScanJay: String)
        fun getScanCarInfoS(list: List<ArrivalScanOperatingMoreInfoBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getScanInfo(billno: String, inoneVehicleFlag: String)
        fun getScanCarInfo(inoneVehicleFlag: String, selEwebidCode: String)

    }
}
