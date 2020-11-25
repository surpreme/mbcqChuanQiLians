package com.mbcq.amountlibrary.fragment.schedulepaymentspending

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-20 16:32:45 待发款明细表
 */

class SchedulePaymentsPendingContract {

    interface View : BaseView {

        fun getPageS(list: List<SchedulePaymentsPendingBean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int)

    }
}
