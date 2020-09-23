package com.mbcq.vehicleslibrary.activity.alllocalagent.fixlocalgentshortfeederhouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-23 11:05:00
 */

class FixedLocalGentShortFeederHouseContract {

    interface View : BaseView {

        fun getInventoryS(list: List<LocalGentShortFeederHouseBean>)
        fun getLoadingDataS(list: List<LocalGentShortFeederHouseBean>)
        fun completeVehicleS()
        fun removeOrderS()
        fun removeOrderItemS(position: Int, item: LocalGentShortFeederHouseBean)
        fun addOrderS()
        fun addOrderItemS(position: Int, item: LocalGentShortFeederHouseBean)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 库存
         */
        fun getInventory()
        fun getLoadingData(agentBillno: String)

        /**
         * 完成本车 车辆
         */
        fun completeVehicle(s: JSONObject)
        fun removeOrder(removeOrderData: JSONObject)
        fun removeOrderItem(removeOrderData: JSONObject,position: Int, item: LocalGentShortFeederHouseBean)
        fun addOrder(removeOrderData: String)
        fun addOrderItem(removeOrderData: String,position: Int, item: LocalGentShortFeederHouseBean)

//        fun removeOrder(commonStr: String, id: String, agentBillno: String, vehcileNo: String)


    }
}
