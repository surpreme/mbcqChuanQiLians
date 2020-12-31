package com.mbcq.orderlibrary.activity.acceptbillingoperationreview

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-12-28 10:25:17 改单申请财务和运营审核
 */

class AcceptBillingFixedReviewContract {

    interface View : BaseView {
        fun getReviewDataS(modifyContent: String, modifyReason: String)
        fun getOrderDataNull(billno: String)
        fun getOrderDataS(data: JSONObject)
        fun postReviewDataS(result: String)


    }

    interface Presenter : BasePresenter<View> {
        fun getReviewData(billno: String)
        fun getOrderData(billno: String)

        //1 运营审核 2财务审核
        fun postReviewData(billno: String, id: String, type: Int)

    }
}
