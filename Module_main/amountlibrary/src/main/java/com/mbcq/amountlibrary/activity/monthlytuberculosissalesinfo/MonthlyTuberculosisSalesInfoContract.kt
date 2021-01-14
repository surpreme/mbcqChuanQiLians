package com.mbcq.amountlibrary.activity.monthlytuberculosissalesinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-13 11:18:49 月结核销详情
 */

class MonthlyTuberculosisSalesInfoContract {

    interface View : BaseView {
        fun getWayBillInfoS(data: JSONObject)
        fun getWayBillInfoNull()

    }

    interface Presenter : BasePresenter<View> {
        fun getWayBillInfo(billno: String)

    }
}
