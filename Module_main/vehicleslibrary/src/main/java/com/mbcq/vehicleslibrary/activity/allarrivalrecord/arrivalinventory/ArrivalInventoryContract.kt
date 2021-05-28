package com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalinventory

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-09-25 18:00:00
 */

class ArrivalInventoryContract {

    interface View : BaseView {

        fun getPageS(list:List<ArrivalInventoryBean>, mArrivalInventoryTotalBean:ArrivalInventoryTotalBean,page: Int, count: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String)
    }
}
