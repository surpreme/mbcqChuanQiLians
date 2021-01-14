package com.mbcq.amountlibrary.activity.allwriteoffreceivepayment.commonreceivewriteoffinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-14 14:09:03 应收核销详情
 */

class CommonReceiveWriteOffInfoContract {

    interface View : BaseView {
        fun getWayBillInfoS(data: JSONObject)
        fun getWayBillInfoNull()
        fun getDepartureCarInfoS(data: CommonDepartureCarInfoBean)
        fun getShortCarInfoS(data: CommonShortCarInfoBean)

    }

    interface Presenter : BasePresenter<View> {
        fun getWayBillInfo(billno: String)
        fun getDepartureCarInfo(inoneVehicleFlag: String)
        fun getShortCarInfo(inoneVehicleFlag: String)
    }
}
