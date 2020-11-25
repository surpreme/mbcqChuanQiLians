package com.mbcq.vehicleslibrary.activity.alldeparturerecord.trunkdeparturehouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class TrunkDepartureHouseContract {

    interface View : BaseView {
        /**
         * 库存运单
         */
        fun getInventoryS(list: List<StockWaybillListBean>)
        fun saveInfoS(s: String)
        fun addStowageAlongWayS(inoneVehicleFlag: String, webidCode: String, webidCodeStr: String, result: String,datalist: HashMap<String, String>,isOver:Boolean)

    }

    interface Presenter : BasePresenter<View> {
        fun getInventory(page: Int)
        fun saveInfo(ob: JSONObject)
        fun addStowageAlongWay(inoneVehicleFlag: String, webidCode: String, webidCodeStr: String,datalist:HashMap<String, String>,isOver:Boolean)

    }
}
