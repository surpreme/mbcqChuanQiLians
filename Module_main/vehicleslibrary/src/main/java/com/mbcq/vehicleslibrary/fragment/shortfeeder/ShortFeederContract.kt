package com.mbcq.vehicleslibrary.fragment.shortfeeder

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-12 13:08：55
 */

class ShortFeederContract {

    interface View : BaseView {
        fun getShortFeederS(list: List<ShortFeederBean>, toltalData: ShortFeederTotalBean)
        fun invalidOrderS()
//        fun searchScanInfoS(dataJSON: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getShortFeeder(page:Int, selWebidCode: String, startDate: String, endDate: String)
        fun invalidOrder(inoneVehicleFlag: String, id: Int)
        fun searchScanInfo(billno: String)
        fun searchBillnoShortFeeder(inoneVehicleFlag: String)
    }
}
