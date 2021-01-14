package com.mbcq.amountlibrary.activity.allwriteoffpayment.commonwriteoff

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2021-01-13 13:45:02 核销记录共用页面
 */

class CommonWriteOffContract {

    interface View : BaseView {
        fun getPaymentedS(list: List<CommonWriteOffBean>)


    }

    interface Presenter : BasePresenter<View> {
        fun getPaymented(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int, mTitle: String)

    }
}
