package com.mbcq.vehicleslibrary.activity.trunkdeparturehouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class TrunkDepartureHouseContract {

    interface View : BaseView {
        /**
         * 库存运单
         */
        fun getInventoryS(list:List<StockWaybillListBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getInventory(page: Int)

    }
}
