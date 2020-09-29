package com.mbcq.orderlibrary.activity.goodsreceipt

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-27 11:18:00
 */

class GoodsReceiptContract {

    interface View : BaseView {

        fun getPageS(list: List<GoodsReceiptBean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int)
        fun getBillNoData(billno: String)

    }
}
