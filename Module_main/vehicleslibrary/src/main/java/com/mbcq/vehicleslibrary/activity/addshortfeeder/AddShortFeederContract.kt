package com.mbcq.vehicleslibrary.activity.addshortfeeder

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-14 13:42:27
 */

class AddShortFeederContract {

    interface View : BaseView {
        fun getDepartureBatchNumberS(inOneVehicleFlag:String)
        fun getVehicleS(result: String)
//        fun geDeliveryPointS(result: String,type:Int)

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 发车批次 和合同编号一样
         */
        fun getDepartureBatchNumber()

        /**
         * 车辆信息
         */
        fun getVehicles()

        /**
         * 到货网点
         */
        fun geDeliveryPoint(type:Int)

    }
}
