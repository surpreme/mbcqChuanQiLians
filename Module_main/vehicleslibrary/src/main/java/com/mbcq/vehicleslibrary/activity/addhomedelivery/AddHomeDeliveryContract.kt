package com.mbcq.vehicleslibrary.activity.addhomedelivery

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2021-01-14 17:09:34 添加上门提货配置
 */

class AddHomeDeliveryContract {

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
