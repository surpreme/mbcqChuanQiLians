package com.mbcq.vehicleslibrary.activity.fixshortfeederhouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-18 14:23:00
 */

class FixShortFeederHouseContract {

    interface View : BaseView {
        fun modifyS()
        fun completeCarS()
        fun addOrderS()
        fun addOrderItemS(position: Int, item: StockWaybillListBean)
        fun removeOrderS()
        fun invalidOrderS()
        fun removeOrderItemS(position: Int, item: StockWaybillListBean)
        fun getCarInfo(result: FixShortFeederHouseCarInfo)
        fun getInventoryS(list: List<StockWaybillListBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun modify(jsonObject: JSONObject)
        fun getInventory(page: Int)
        fun getCarInfo(id: Int, inoneVehicleFlag: String)
        fun removeOrder(commonStr: String, id: String, inoneVehicleFlag: String)
        fun removeOrderItem(commonStr: String, id: String, inoneVehicleFlag: String,position: Int, item: StockWaybillListBean)
        fun addOrderItem(commonStr: String, id: String, inoneVehicleFlag: String,position: Int, item: StockWaybillListBean)
        fun addOrder(commonStr: String, id: String, inoneVehicleFlag: String,dbVehicleDetLst:List<StockWaybillListBean> )
        //完成本车
        fun completeCar(id: Int, inoneVehicleFlag: String)
        //作废本车
        fun invalidOrder(inoneVehicleFlag: String, id: Int)


    }
}
