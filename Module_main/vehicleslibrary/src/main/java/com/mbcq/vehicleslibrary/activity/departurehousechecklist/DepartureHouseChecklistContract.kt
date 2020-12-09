package com.mbcq.vehicleslibrary.activity.departurehousechecklist

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.shorthousechecklist.ShortHouseChecklistBean

/**
 * @author: lzy
 * @time: 2020-12-09 14:14:10 干线在库清单
 */

class DepartureHouseChecklistContract {

    interface View : BaseView {
        fun getInventoryS(list: List<DepartureHouseChecklistBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getInventory(page: Int)

    }
}
