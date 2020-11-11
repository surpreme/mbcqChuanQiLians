package com.mbcq.orderlibrary.activity.receipt.receiptconsignment

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-10 18:11:43 回单寄出
 */

class ReceiptConsignmentContract {

    interface View : BaseView {
        fun getPageS(list:List<ReceiptConsignmentBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String)

    }
}
