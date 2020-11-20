package com.mbcq.amountlibrary.activity.loanchange

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-16 10:16:03 贷款变更
 */

class LoanChangeContract {

    interface View : BaseView {
        fun getWaybillDetailS(data: JSONObject)
        fun getWaybillDetailNull()
        fun changeOrderS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getWaybillDetail(billNo: String)
        fun changeOrder(jsonObject: JSONObject)

    }
}
