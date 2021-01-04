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
        fun getWillByInfoS(data: JSONObject, resultBillno: String, mScanType: Int)
        fun getWillByInfoNull(billno: String)
        fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String)
        fun saveScanPostS(result: String)
        fun getCarInfoS(list: List<DepartureTrunkDepartureUnPlanScanOperatingBean>, id: Int)
        fun getVehicleS(result: String)

        /**
         * 不在库存中
         */
        fun isNotAtStock(billno: String)

        /**
         * 再次扫描异常
         */
        fun againScanException(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mScanType: Int, errorStr: String)
        fun getScanBillNoInfoS(billno: String, result: String, totalQty: Int)

    }

    interface Presenter : BasePresenter<View> {
        fun getWillByInfo(billno: String, resultBillno: String, mScanType: Int)
        fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, totalQty: Int, mScanType: Int)
        fun saveScanPost(id: Int, inoneVehicleFlag: String)
        fun getCarInfo(inoneVehicleFlag: String)

        /**
         * 车辆信息
         */
        fun getVehicles(vehicleNo: String)

        /**
         * 异常扫描
         */
        fun scanAbnormalOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mMoreScanBillno: String, mAbnormalReason: String)

        /**
         * 获取扫描信息
         */
        fun getScanBillNoInfo(billno: String, totalQty: Int)
    }
}
