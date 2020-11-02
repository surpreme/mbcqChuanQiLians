package com.mbcq.vehicleslibrary.fragment.arrivalshortfeederscan

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.fragment.shortfeeder.ShortFeederBean

/**
 * @author: lzy
 * @time: 2020-10-29 14:54:32
 */

class ArrivalShortFeederScanFragmentContract {

    interface View : BaseView {
        fun getPageS(list: List<ArrivalShortFeederScanBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getUnLoading(selEwebidCode: String, startDate: String, endDate: String)
        fun getLoading(selEwebidCode: String, startDate: String, endDate: String)

    }
}
