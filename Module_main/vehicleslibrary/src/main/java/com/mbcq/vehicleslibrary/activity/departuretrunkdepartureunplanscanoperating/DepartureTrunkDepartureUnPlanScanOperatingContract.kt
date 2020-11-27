package com.mbcq.vehicleslibrary.activity.departuretrunkdepartureunplanscanoperating

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating.ShortTrunkDepartureUnPlanScanOperatingBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-19 09:49:03 干线无计划扫描
 */

class DepartureTrunkDepartureUnPlanScanOperatingContract {

    interface View : BaseView {
        fun getWillByInfoS(data: JSONObject, resultBillno: String)
        fun getWillByInfoNull()
        fun scanOrderS(billno: String, soundStr: String,mMoreScanBillno:String)
        fun saveScanPostS(result: String)
        fun getCarInfoS(list: List<DepartureTrunkDepartureUnPlanScanOperatingBean>, id: Int)

    }

    interface Presenter : BasePresenter<View> {
        fun getWillByInfo(billno: String, resultBillno: String)
        fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String)
        fun saveScanPost(id: Int, inoneVehicleFlag: String)
        fun getCarInfo(inoneVehicleFlag: String)

    }
}
