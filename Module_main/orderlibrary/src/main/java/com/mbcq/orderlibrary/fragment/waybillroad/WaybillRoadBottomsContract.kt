package com.mbcq.orderlibrary.fragment.waybillroad

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class WaybillRoadBottomsContract {

    interface View : BaseView {
        fun getTrackRoadS(list: List<WaybillRoadBottomBean>)

    }

    interface Presenter : BasePresenter<View> {

        fun getTrackRoad(billno: String)
    }
}
