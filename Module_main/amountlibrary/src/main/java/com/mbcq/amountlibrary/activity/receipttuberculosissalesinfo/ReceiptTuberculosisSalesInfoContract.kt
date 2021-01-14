package com.mbcq.amountlibrary.activity.receipttuberculosissalesinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class ReceiptTuberculosisSalesInfoContract {

    interface View : BaseView {
        fun getWayBillInfoS(data: JSONObject)
        fun getWayBillInfoNull()

    }

    interface Presenter : BasePresenter<View> {
        fun getWayBillInfo(billno: String)

    }
}
