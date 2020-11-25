package com.mbcq.vehicleslibrary.activity.shorttrunkdeparturescanoperating

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-04 16:05:23 短驳发车
 */

class ShortTrunkDepartureScanOperatingContract {

    interface View : BaseView {
        fun getCarInfoS(list: List<ShortTrunkDepartureScanOperatingBean>)
        fun scanOrderS(billno: String, soundStr: String, mMoreScanBillno: String)
        fun saveScanPostS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getCarInfo(inoneVehicleFlag: String)
        fun scanOrder(billno: String, lableNo: String, deviceNo: String, inOneVehicleFlag: String, soundStr: String, scanPercentage: String)
        fun saveScanPost(id: Int, inoneVehicleFlag: String)

    }
}
