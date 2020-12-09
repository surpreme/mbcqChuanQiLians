package com.mbcq.vehicleslibrary.activity.shorthousechecklist

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.bean.StockWaybillListBean

/**
 * @author: lzy
 * @time: 2020-12-09 13:16:43 短驳在库清单
 */

class ShortHouseChecklistContract {

    interface View : BaseView {
        fun getInventoryS(list: List<ShortHouseChecklistBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getInventory(page: Int)

    }
}
