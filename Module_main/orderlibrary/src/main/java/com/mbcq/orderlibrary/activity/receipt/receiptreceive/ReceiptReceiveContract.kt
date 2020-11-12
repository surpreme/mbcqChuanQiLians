package com.mbcq.orderlibrary.activity.receipt.receiptreceive

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.orderlibrary.activity.receipt.receiptconsignment.ReceiptConsignmentBean

/**
 * @author: lzy
 * @time: 2020-11-11 08:58:16 回单接收
 */

class ReceiptReceiveContract {

    interface View : BaseView {
        fun getPageS(list: List<ReceiptReceiveBean>)
        fun completeS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String)
        fun complete(jsonStr: String)

    }
}
