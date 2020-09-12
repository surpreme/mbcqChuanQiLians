package com.mbcq.vehicleslibrary.fragment.shortfeeder

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-12 13:08：55
 */

class ShortFeederContract {

    interface View : BaseView {
        fun getShortFeederS(list: List<ShortFeederBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getShortFeeder(page:Int)

    }
}
