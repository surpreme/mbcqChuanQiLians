package com.mbcq.orderlibrary.activity.receipt.receiptcancel

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-12 15:04:02 回单取消
 */

class ReceiptCancelContract {

    interface View : BaseView {
        fun searchOrderS(list: List<ReceiptCancelBean>)
        fun deleteOrderStateS(position: Int, result: String)
    }

    interface Presenter : BasePresenter<View> {
        fun searchOrder(billno: String)
        fun deleteOrderState(czType: String, id: String,position: Int)

    }
}
