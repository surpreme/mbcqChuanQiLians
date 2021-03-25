package com.mbcq.vehicleslibrary.activity.arrivaltrunkdeparturescanoperating

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.arrivalscanoperatingmoreinfo.ArrivalScanOperatingMoreInfoBean

/**
 * @author: lzy
 * @time: 2020-10-30 09:24:35 到车
 */

class ArrivalTrunkDepartureScanOperatingContract {

    interface View : BaseView {
        fun getCarInfoS(list: List<ArrivalTrunkDepartureScanOperatingBean>, id: Int)
        fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String)

        fun getClickLableS(result: String)
        fun getScanDataS(result: String)

    }

    interface Presenter : BasePresenter<View> {

        fun getClickLable(billno: String, inoneVehicleFlag: String, totalQty: Int)

        fun getScanData(billno: String, lableNo: String, inoneVehicleFlag: String)

        fun getCarInfo(inoneVehicleFlag: String,selEwebidCode:String)
        fun scanOrder(billno: String, lableNo: String, inoneVehicleFlag: String, ewebidCodeStr: String, scanType: Int, totalQty: Int, xcScanPercentage: String)
    }
}
