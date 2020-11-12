package com.mbcq.orderlibrary.activity.receipt.receiptgeneralledger

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-12 09:23:05 回单总账
 */

class ReceiptGeneralLedgerContract {

    interface View : BaseView {
        fun getPageS(list: List<ReceiptGeneralLedgerBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int)

    }
}
