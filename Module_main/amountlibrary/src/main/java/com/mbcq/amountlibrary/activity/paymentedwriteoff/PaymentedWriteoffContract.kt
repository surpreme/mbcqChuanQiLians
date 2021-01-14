package com.mbcq.amountlibrary.activity.paymentedwriteoff

import com.mbcq.amountlibrary.activity.paymentingwriteoff.PaymentingWriteoffBean
import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-01-09 16:11:17 提付核销
 */

class PaymentedWriteoffContract {

    interface View : BaseView {
        fun getPaymentedS(list: List<PaymentedWriteoffBean>)


    }

    interface Presenter : BasePresenter<View> {
        fun getPaymented(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int)

    }
}
