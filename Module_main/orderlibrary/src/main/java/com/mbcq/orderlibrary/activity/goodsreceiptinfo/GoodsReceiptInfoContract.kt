package com.mbcq.orderlibrary.activity.goodsreceiptinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-27 14:40:05
 */

class GoodsReceiptInfoContract {

    interface View : BaseView {
        fun getPaymentWayS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getPaymentWay()

    }
}
