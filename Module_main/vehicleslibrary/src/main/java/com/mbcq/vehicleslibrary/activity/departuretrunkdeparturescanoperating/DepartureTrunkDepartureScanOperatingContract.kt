package com.mbcq.vehicleslibrary.activity.departuretrunkdeparturescanoperating

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-04 14:30 :21 干线扫描发车
 */

class DepartureTrunkDepartureScanOperatingContract {

    interface View : BaseView {
        fun getCarInfoS(list: List<DepartureTrunkDepartureScanOperatingBean>)
        fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String)
        fun saveScanPostS(result: String)
        fun getWillByInfoS(data: JSONObject, resultBillno: String, mScanType: Int)
        fun getWillByInfoNull(billno: String)

        /**
         * 不在库存中
         */
        fun isNotAtStock(billno: String)
        fun againScanException(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, scanPercentage: String, mScanType: Int, errorInfoStr: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getCarInfo(inoneVehicleFlag: String)
        fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, scanPercentage: String, totalQty: Int, mScanType: Int)

        /**
         * 异常扫描
         */
        fun scanAbnormalOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mMoreScanBillno: String, mAbnormalReason: String)
        fun scanOrderUNPlan(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String,scanPercentage: String, totalQty: Int,  mScanType: Int)
        fun getWillByInfo(billno: String, resultBillno: String, mScanType: Int)
        fun saveScanPost(id: Int, inoneVehicleFlag: String)

    }
}
