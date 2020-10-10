package com.mbcq.orderlibrary.activity.fixeddeliverysomethinghouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.orderlibrary.activity.deliverysomethinghouse.DeliverySomethingHouseBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-09 11:00:00 修改送货上门
 */

class FixedDeliverySomethingHouseContract {

    interface View : BaseView {
        fun getInventoryS(list:List<FixedDeliverySomethingHouseBean>)
        fun getLoadingInventory(list:List<FixedDeliverySomethingHouseBean>)
        fun addOrderItemS(position: Int, item: FixedDeliverySomethingHouseBean)
        fun removeOrderItemS(position: Int, item: FixedDeliverySomethingHouseBean)
        fun addOrderS()
        fun removeOrderS()

    }

    interface Presenter : BasePresenter<View> {

        fun getInventory()
        fun getLoadingInventory(id:String,sendInOneFlag: String)
        fun addOrderItem(removeOrderData: String,position: Int, item: FixedDeliverySomethingHouseBean)
        fun addOrder(removeOrderData: String)
        fun removeOrderItem(commonStr: String, id: String, inoneVehicleFlag: String, position: Int, item: FixedDeliverySomethingHouseBean)
        fun removeOrder(commonStr: String, id: String, inoneVehicleFlag: String)

    }
}
