package com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-22 17:13:27
 */

class LocalGentShortFeederHouseContract {

    interface View : BaseView {

        fun getInventoryS(list:List<LocalGentShortFeederHouseBean>)
        fun  completeVehicleS()
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 库存
         */
        fun getInventory()


        /**
         * 完成本车 车辆
         */
        fun completeVehicle(s: JSONObject)

    }
}
