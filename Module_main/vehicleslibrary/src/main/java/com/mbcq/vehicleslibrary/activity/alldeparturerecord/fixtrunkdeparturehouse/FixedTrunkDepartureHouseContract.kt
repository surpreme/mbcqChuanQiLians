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

        //添加沿途配载网点
        fun addStowageAlongWayS(inoneVehicleFlag: String, webidCode: String, webidCodeStr: String, result: String)
        fun getStowageAlongWayS(list: List<FixedStowageAlongWayBean>)
        fun deleteStowageAlongWay(result: String)
        fun getVehicleS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getCarInfo(id: Int, inoneVehicleFlag: String)
        fun addOrder(commonStr: String, id: String, inoneVehicleFlag: String, gxVehicleDetLst: List<StockWaybillListBean>)
        fun removeOrderItem(commonStr: String, id: String, inoneVehicleFlag: String, position: Int, item: StockWaybillListBean)
        fun addOrderItem(commonStr: String, id: String, inoneVehicleFlag: String, position: Int, item: StockWaybillListBean)
        fun modify(jsonObject: JSONObject)
        fun removeOrder(commonStr: String, id: String, inoneVehicleFlag: String)
        fun getInventory(page: Int, ewebidCode: String, ewebidCodeStr: String)

        //作废本车
        fun invalidOrder(inoneVehicleFlag: String, id: Int)

        //完成本车
        fun completeCar(id: Int, inoneVehicleFlag: String)

        //添加沿途配载网点
        fun addStowageAlongWay(inoneVehicleFlag: String, webidCode: String, webidCodeStr: String)
        fun getStowageAlongWay(inoneVehicleFlag: String, id: Int)

        /**
         * 后台无法修改 这里删除所有 然后前台根据顺序添加即可
         */
        fun deleteStowageAlongWay(inoneVehicleFlag: String)
        /**
         * 车辆信息
         */
        fun getVehicles(vehicleNo:String)
    }
}
