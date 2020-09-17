package com.mbcq.vehicleslibrary.activity.shortfeederhouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-15 10:12:09
 */

class ShortFeederHouseContract {

    interface View : BaseView {
        fun saveInfoS(result: String)
        fun getInventoryS(list:List<StockWaybillListBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun saveInfo(ob: JSONObject)
        fun getInventory(page: Int)

    }
}
