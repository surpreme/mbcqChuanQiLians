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

    }

    interface Presenter : BasePresenter<View> {

        fun getInventory()
        fun removeOrder(removeOrderData: JSONObject)
        fun removeOrderItem(removeOrderData: JSONObject, position: Int, item: DeliverySomethingHouseBean)
        fun addOrder(removeOrderData: String)
        fun addOrderItem(removeOrderData: String,position: Int, item: DeliverySomethingHouseBean)
    }
}
