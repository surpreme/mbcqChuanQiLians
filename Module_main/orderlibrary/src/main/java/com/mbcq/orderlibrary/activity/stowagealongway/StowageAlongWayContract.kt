package com.mbcq.orderlibrary.activity.stowagealongway

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-11-21 15:48:12 沿途配载
 */

class StowageAlongWayContract {

    interface View : BaseView {
        fun  getPageS(list: List<StowageAlongWayBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int)

    }
}
