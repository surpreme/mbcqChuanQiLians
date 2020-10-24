package com.mbcq.orderlibrary.activity.printacceptbilling

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-24 14:18:42 运单标签补打印
 */

class PrintAcceptBillingContract {

    interface View : BaseView {
        fun getWayBillInfoS(data: JSONObject, dataStr: String)
        fun getWayBillInfoNull()
    }

    interface Presenter : BasePresenter<View> {
        fun getWayBillInfo(billno: String)

    }
}
