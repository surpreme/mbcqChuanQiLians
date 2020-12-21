package com.mbcq.vehicleslibrary.activity.shorttrunkdepartureunplanscanoperating

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-13 13:40:46 短驳扫描无计划装车
 */

class ShortTrunkDepartureUnPlanScanOperatingContract {

    interface View : BaseView {
        fun getWillByInfoS(data: JSONObject, resultBillno: String, mScanType: Int)
        fun getWillByInfoNull()
        fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String, mResultMoreData: String)
        fun getCarInfoS(list: List<ShortTrunkDepartureUnPlanScanOperatingBean>, id: Int)
        fun saveScanPostS(result: String)
        /**
         * 不在库存中
         */
        fun isNotAtStock(billno: String)
        /**
         * 再次扫描异常
         */
        fun againScanException(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mScanType: Int, errorStr: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getWillByInfo(billno: String, resultBillno: String, mScanType: Int)
        fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, totalQty: Int, mMoreScanBillno: String, mScanType: Int)

        /**
         * 异常扫描
         */
        fun scanAbnormalOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mMoreScanBillno: String,mAbnormalReason:String)
        fun getCarInfo(inoneVehicleFlag: String)
        fun saveScanPost(id: Int, inoneVehicleFlag: String)

    }
}
