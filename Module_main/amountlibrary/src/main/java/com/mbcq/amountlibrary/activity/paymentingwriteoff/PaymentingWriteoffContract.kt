package com.mbcq.amountlibrary.activity.paymentingwriteoff

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-01-09 15:23:07 现付核销
 */

class PaymentingWriteoffContract {

    interface View : BaseView {
        fun getPaymentingS(list: List<PaymentingWriteoffBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPaymenting(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int)



    }
}
