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
        fun getCarInfo(result: FixShortFeederHouseCarInfo)
        fun getInventoryS(list: List<StockWaybillListBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun modify(jsonObject: JSONObject)
        fun getInventory(page: Int)
        fun getCarInfo(id: Int, inoneVehicleFlag: String)

    }
}
