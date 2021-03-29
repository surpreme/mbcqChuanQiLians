package com.mbcq.vehicleslibrary.activity.homedeliveryhouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-01-14 17:59:02
 */

class HomeDeliveryHouseContract {

    interface View : BaseView {
        fun getInventoryS(list: List<HomeDeliveryHouseBean>)
        fun saveInfoS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 库存
         */
        fun getInventory()
        fun saveInfo(ob: JSONObject)

    }
}
