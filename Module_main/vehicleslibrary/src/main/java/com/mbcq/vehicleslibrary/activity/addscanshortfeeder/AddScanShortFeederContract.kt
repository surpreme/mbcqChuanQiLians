package com.mbcq.vehicleslibrary.activity.addscanshortfeeder

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-12 17:42:45 无计划短驳装车
 */

class AddScanShortFeederContract {

    interface View : BaseView {
        fun getDepartureBatchNumberS(inOneVehicleFlag: String)
        fun getVehicleS(result: String)
        fun saveInfoS(result: String)
        fun modifyS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getDepartureBatchNumber()

        /**
         * 车辆信息
         */
        fun getVehicles()

        /**
         * 保存
         */
        fun saveInfo(ob: JSONObject)
        fun modify(jbtStr: String)


    }
}
