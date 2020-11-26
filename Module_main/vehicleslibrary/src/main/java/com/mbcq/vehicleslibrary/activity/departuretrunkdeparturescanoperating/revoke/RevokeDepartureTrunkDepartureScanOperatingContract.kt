package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating.revoke

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke.RevokeShortTrunkDepartureScanOperatingBean

/**
 * @author: lzy
 * @time: 2020-11-19 08:31:06 干线撤销扫描
 */

class RevokeDepartureTrunkDepartureScanOperatingContract {

    interface View : BaseView {
        fun revokeOrderS(result: String, mMoreScanBillno: String)
        fun getCarInfoS(list: List<RevokeDepartureTrunkDepartureScanOperatingBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun revokeOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String,scanPercentage:String)
        fun getCarInfo(inoneVehicleFlag: String)

    }
}
