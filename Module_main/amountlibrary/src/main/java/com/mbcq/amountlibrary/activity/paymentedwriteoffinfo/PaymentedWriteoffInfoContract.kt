package com.mbcq.amountlibrary.activity.paymentedwriteoffinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-12 11:04:17 提付核销详情页
 */

class PaymentedWriteoffInfoContract {

    interface View : BaseView {
        fun getWayBillInfoS(data: JSONObject)
        fun getWayBillInfoNull()

    }

    interface Presenter : BasePresenter<View> {
        fun getWayBillInfo(billno: String)

    }
}
