package com.mbcq.orderlibrary.activity.receipt.receiptsign

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-07 09:06:21 回单签收
 */

class ReceiptSignContract {

    interface View : BaseView {
        fun getPageS(list: List<ReceiptSignBean>)
        fun searchOrderS(list: List<ReceiptSignBean>)

        fun completeS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String)
        fun complete(jsonStr: String)
        fun searchOrder(billno: String)
    }
}
