package com.mbcq.amountlibrary.activity.allwriteoffpayment.commonwriteoffinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-13 15:11:10 核销详情
 */

class CommonWriteOffInfoContract {

    interface View : BaseView {
        fun getWayBillInfoS(data: JSONObject)
        fun getWayBillInfoNull()

    }

    interface Presenter : BasePresenter<View> {
        fun getWayBillInfo(billno: String)
    }
}
