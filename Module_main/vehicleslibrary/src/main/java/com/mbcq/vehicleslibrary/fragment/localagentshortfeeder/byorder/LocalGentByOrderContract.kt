package com.mbcq.vehicleslibrary.fragment.localagentshortfeeder.byorder

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.fragment.localagentshortfeeder.bycar.LocalGentByCarBean

/**
 * @author: lzy
 * @time: 2020-12-30 14:19:34 本地代理 按票
 */

class LocalGentByOrderContract {

    interface View : BaseView {
        fun getPageS(list: List<LocalGentByOrderBean>)
        fun cancelS( position: Int)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int,selEwebidCode: String, startDate: String, endDate: String)
        fun cancel(s: LocalGentByOrderBean, position: Int)

    }
}
