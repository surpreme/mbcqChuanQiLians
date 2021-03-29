package com.mbcq.vehicleslibrary.activity.fixhomedeliveryconfiguration

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2021-03-27 17:53:06 修改上门提货配置
 */

class FixHomeDeliveryConfigurationContract {

    interface View : BaseView {
        fun getBatchS(result: String)
        fun getVehicleS(result: String)

    }

    interface Presenter : BasePresenter<View> {

        /**
         * 获取 批次
         */
        fun getBatch()
        fun getVehicles()

    }
}
