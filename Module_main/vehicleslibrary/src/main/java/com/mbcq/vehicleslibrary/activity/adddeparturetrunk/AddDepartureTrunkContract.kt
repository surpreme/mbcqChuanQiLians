package com.mbcq.vehicleslibrary.activity.adddeparturetrunk

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-19 10:14:45 添加无计划干线扫描
 */

class AddDepartureTrunkContract {

    interface View : BaseView {
        fun getVehicleS(result: String)
        fun getDepartureBatchNumberS(result: String)
        fun saveInfoS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getVehicles()
        fun getDepartureBatchNumber()
        /**
         * 保存
         */
        fun saveInfo(ob: JSONObject)
    }
}
