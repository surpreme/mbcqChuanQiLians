package com.mbcq.amountlibrary.activity.receiptmonthlytuberculosissales

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2021-01-13 08:57:10 回单付核销
 */

class ReceiptMonthlyTuberculosisSalesContract {

    interface View : BaseView {
        fun getMonthlyS(list: List<ReceiptMonthlyTuberculosisSalesBean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getMonthly(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int)
    }
}
