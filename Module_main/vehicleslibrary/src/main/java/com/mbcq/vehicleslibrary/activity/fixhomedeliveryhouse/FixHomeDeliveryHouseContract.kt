package com.mbcq.vehicleslibrary.activity.fixhomedeliveryhouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean
import com.mbcq.vehicleslibrary.activity.homedeliveryhouse.HomeDeliveryHouseBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-03-27 09:22:17 修改上门提货
 */

class FixHomeDeliveryHouseContract {

    interface View : BaseView {
        fun getInventoryS(list: List<HomeDeliveryHouseBean>)
        fun getLoadingS(list: List<HomeDeliveryHouseBean>)
//        fun removeOrderItemS(position: Int, item: HomeDeliveryHouseBean)
//        fun addOrderItemS(position: Int, item: HomeDeliveryHouseBean)
        fun removeOrderS()
        fun addOrderS()
        fun overOrderS()
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 库存
         */
        fun getInventory()

        /**
         *
         */
        fun getLoading(id: String)
        fun overOrder(orderData: String,id: String,intentJson:String)
        fun removeOrder(removeOrderData: String)
        fun addOrder(removeOrderData: String)
        fun removeOrderItem(removeOrderData: String, position: Int, item: HomeDeliveryHouseBean)
        fun addOrderItem(removeOrderData: String, position: Int, item: HomeDeliveryHouseBean)
    }
}
