package com.mbcq.vehicleslibrary.activity.alllocalagent.addlocalgentshortfeeder

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-22 15:48
 */

class AddLocalGentShortFeederContract {

    interface View : BaseView {
        fun getDepartureBatchNumberS(inOneVehicleFlag: String)
        fun getVehicleS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 发车批次号
         */
        fun getDepartureBatchNumber()
        fun getVehicles()

    }
}
