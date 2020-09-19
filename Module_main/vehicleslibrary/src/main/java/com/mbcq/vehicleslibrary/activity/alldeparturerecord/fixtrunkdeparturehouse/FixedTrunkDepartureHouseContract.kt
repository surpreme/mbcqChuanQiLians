package com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixtrunkdeparturehouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-18 11:33
 */

class FixedTrunkDepartureHouseContract {

    interface View : BaseView {
        fun addOrderS()
        fun removeOrderS()
        fun getCarInfo(result: FixedTrunkDepartureHouseInfo)
        fun modifyS()
        fun getInventoryS(list: List<StockWaybillListBean>)
        fun invalidOrderS()
        fun removeOrderItemS(position: Int, item: StockWaybillListBean)
        fun addOrderItemS(position: Int, item: StockWaybillListBean)
        fun completeCarS()

    }

    interface Presenter : BasePresenter<View> {
        fun getCarInfo(id: Int, inoneVehicleFlag: String)
        fun addOrder(commonStr: String, id: String, inoneVehicleFlag: String, gxVehicleDetLst: List<StockWaybillListBean>)
        fun removeOrderItem(commonStr: String, id: String, inoneVehicleFlag: String,position: Int, item: StockWaybillListBean)
        fun addOrderItem(commonStr: String, id: String, inoneVehicleFlag: String,position: Int, item: StockWaybillListBean)
        fun modify(jsonObject: JSONObject)
        fun removeOrder(commonStr: String, id: String, inoneVehicleFlag: String)
        fun getInventory(page: Int)
        //作废本车
        fun invalidOrder(inoneVehicleFlag: String, id: Int)
        //完成本车
        fun completeCar(id: Int, inoneVehicleFlag: String)
    }
}
