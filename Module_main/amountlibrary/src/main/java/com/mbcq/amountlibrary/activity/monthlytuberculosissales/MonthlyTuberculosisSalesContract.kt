package com.mbcq.amountlibrary.activity.monthlytuberculosissales

import com.mbcq.amountlibrary.activity.paymentedwriteoff.PaymentedWriteoffBean
import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2021-01-13 10:33:09 月结核销
 */

class MonthlyTuberculosisSalesContract {

    interface View : BaseView {
        fun getMonthlyS(list: List<MonthlyTuberculosisSalesBean>)


    }

    interface Presenter : BasePresenter<View> {
        fun getMonthly(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int)

    }
}
