package com.mbcq.vehicleslibrary.activity.stowagealongwayhouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.alldeparturerecord.fixtrunkdeparturehouse.FixedTrunkDepartureHouseInfo
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-11-23 08:57:43 沿途配载操作详情页
 */

class StowageAlongWayHouseContract {

    interface View : BaseView {
        fun getCarInfo(result: FixedTrunkDepartureHouseInfo)
        fun getInventoryS(list: List<StockWaybillListBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getCarInfo(id: Int, inoneVehicleFlag: String)
        fun getInventory(page: Int)
        fun addOrder(commonStr: String, id: String, inoneVehicleFlag: String, gxVehicleDetLst: List<StockWaybillListBean>)

    }
}
