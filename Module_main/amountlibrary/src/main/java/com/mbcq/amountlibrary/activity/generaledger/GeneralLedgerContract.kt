package com.mbcq.amountlibrary.activity.generaledger

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-20 17:31:12 货款总账
 */

class GeneralLedgerContract {

    interface View : BaseView {
        fun getPageS(list: List<GeneralLedgerBean>)
    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int)

    }
}
