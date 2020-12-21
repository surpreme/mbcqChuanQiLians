package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating.revoke

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating.revoke.RevokeDepartureTrunkDepartureScanOperatingBean

/**
 * @author: lzy
 * @time: 2020-11-10 10:02:03 撤销干线装车扫描
 */

class RevokeShortTrunkDepartureScanOperatingContract {

    interface View : BaseView {
        fun revokeOrderS(result: String, mMoreScanBillno: String)
        fun getCarInfoS(list: List<RevokeShortTrunkDepartureScanOperatingBean>)
        fun getScanDataS(list: ArrayList<Long>,lableNo: String, mRevokeShortTrunkDepartureScanOperatingBean: RevokeShortTrunkDepartureScanOperatingBean)

    }

    interface Presenter : BasePresenter<View> {
        fun revokeOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String,scanPercentage:String)
        fun getCarInfo(inoneVehicleFlag: String)
        fun getScanData(billno: String,lableNo: String, inOneVehicleFlag: String, scanOpeType: Int,mXRevokeShortTrunkDepartureScanOperatingBean:RevokeShortTrunkDepartureScanOperatingBean)

    }
}
