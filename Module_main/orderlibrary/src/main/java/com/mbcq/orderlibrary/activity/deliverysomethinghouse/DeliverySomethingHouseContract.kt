package com.mbcq.orderlibrary.activity.deliverysomethinghouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time:
 */

class DeliverySomethingHouseContract {

    interface View : BaseView {
        fun getInventoryS(list:List<DeliverySomethingHouseBean>)
        fun saveInfoS(result: String)
        fun getDepartureS(s:String)

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 发车批次
         */
        fun getDeparture()
        fun getInventory()
        fun saveInfo(ob: JSONObject)

    }
}
