package com.mbcq.orderlibrary.activity.adddeliverysomething

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-09 10:44:13
 */

class AddDeliverySomeThingContract {

    interface View : BaseView {
        fun getDepartureS(s:String)
        fun getVehicleS(result:String)

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 发车批次
         */
        fun getDeparture()
        fun getVehicles()

    }
}
