package com.mbcq.vehicleslibrary.activity.arrivalshortscanoperating

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating.ArrivalTrunkDepartureScanOperatingBean

/**
 * @author: lzy
 * @time: 2021-03-19 10:30:12 短驳到车扫描
 */

class ArrivalShortScanOperatingContract {

    interface View : BaseView {
        fun getCarInfoS(list: List<ArrivalShortScanOperatingBean>, id: Int)
        fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String)

        //        fun getUnScanBillnoDataS(result: String)
        fun getClickLableS(result: String)
        fun getScanDataS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getClickLable(billno: String, inoneVehicleFlag: String, totalQty: Int, type: Int)
        fun getScanData(billno: String, lableNo: String, inoneVehicleFlag: String)

        fun getCarInfo(inoneVehicleFlag: String)
        fun scanOrder(billno: String, lableNo: String, inoneVehicleFlag: String, ewebidCodeStr: String, scanType: Int, totalQty: Int, xcScanPercentage: String)
    }
}
