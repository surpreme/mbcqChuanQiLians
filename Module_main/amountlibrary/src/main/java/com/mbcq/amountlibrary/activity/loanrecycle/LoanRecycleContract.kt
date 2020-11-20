package com.mbcq.amountlibrary.activity.loanrecycle

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-20 09:15:45 货款回收
 */

class LoanRecycleContract {

    interface View : BaseView {
        fun getPageS(list: List<LoanRecycleBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int)

    }
}
