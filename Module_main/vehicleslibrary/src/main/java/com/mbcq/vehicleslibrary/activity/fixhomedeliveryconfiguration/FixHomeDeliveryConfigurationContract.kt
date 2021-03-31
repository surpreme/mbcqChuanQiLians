package com.mbcq.vehicleslibrary.activity.fixhomedeliveryconfiguration

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2021-03-27 17:53:06 修改上门提货配置
 */

class FixHomeDeliveryConfigurationContract {

    interface View : BaseView {
        fun getVehicleS(result: String)
        fun fixConfiguration(result: String)


    }

    interface Presenter : BasePresenter<View> {


        fun getVehicles()
        fun fixConfiguration(dataStr:String)

    }
}
