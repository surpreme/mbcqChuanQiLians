package com.mbcq.orderlibrary.activity.addclaimsettlement

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-02 17:56:13 添加 理赔
 */

class AddClaimSettlementContract {

    interface View : BaseView {
        fun getOrderInfoS(data: JSONObject)
        fun getOrderInfoNull()
    }

    interface Presenter : BasePresenter<View> {

        fun getOrderInfo(billno: String)
    }
}
