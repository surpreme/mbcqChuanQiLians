package com.mbcq.orderlibrary.activity.receipt.receiptreturnfactory

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.orderlibrary.activity.receipt.receiptconsignment.ReceiptConsignmentBean

/**
 * @author: lzy
 * @time: 2020-11-11 09:02:45 回单返厂
 */

class ReceiptReturnFactoryContract {

    interface View : BaseView {
        fun getPageS(list:List<ReceiptReturnFactoryBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String)

    }
}
