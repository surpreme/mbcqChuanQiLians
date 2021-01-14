package com.mbcq.amountlibrary.activity.allwriteoffreceivepayment.commonreceivewriteoff

import com.mbcq.amountlibrary.activity.allwriteoffpayment.commonwriteoff.CommonWriteOffBean
import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2021-01-14 09:21:07 应收核销记录
 */

class CommonReceiveWriteOffContract {

    interface View : BaseView {
        fun getPaymentedS(list: List<CommonReceiveWriteOffBean>)


    }

    interface Presenter : BasePresenter<View> {
        fun getPaymented(page: Int, selWebidCode: String, startDate: String, endDate: String, type: Int, mTitle: String)

    }
}
