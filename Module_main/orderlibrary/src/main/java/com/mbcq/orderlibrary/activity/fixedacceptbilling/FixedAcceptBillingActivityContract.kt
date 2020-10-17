package com.mbcq.orderlibrary.activity.fixedacceptbilling

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-10-17 13:32:00 改单申请
 */

class FixedAcceptBillingActivityContract {

    interface View : BaseView {
        fun getTransportModeS(result: String)
        fun getPaymentModeS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 付款方式
         */
        fun getPaymentMode()
        /**
         * 运输方式
         */
        fun getTransportMode()
    }
}
