package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-04 16:05:23 短驳发车
 */

class ShortTrunkDepartureScanOperatingContract {

    interface View : BaseView {
        fun getCarInfoS(list: List<ShortTrunkDepartureScanOperatingBean>)
        fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String)
        fun saveScanPostS(result: String)
        fun getWillByInfoS(data: JSONObject, resultBillno: String, mScanType: Int)
        fun getWillByInfoNull()

        /**
         * 不在库存中
         */
        fun isNotAtStock(billno: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getCarInfo(inoneVehicleFlag: String)

        /**
         * 异常扫描
         */
        fun scanAbnormalOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, ewebidCode: String, ewebidCodeStr: String, scanPercentage: String, mMoreScanBillno: String, mAbnormalReason: String)
        fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, scanPercentage: String, mScanType: Int)
        fun scanOrderUNPlan(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, scanPercentage: String, mScanType: Int)
        fun getWillByInfo(billno: String, resultBillno: String, mScanType: Int)
        fun saveScanPost(id: Int, inoneVehicleFlag: String)

    }
}
