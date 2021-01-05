package com.mbcq.vehicleslibrary.activity.alldeparturerecord.shipmentinventory

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.allarrivalrecord.arrivalinventory.ArrivalInventoryBean

/**
 * @author: lzy
 * @time: 2020-09-27 09:47:00
 */

class ShipmentInventoryContract {

    interface View : BaseView {

        fun getPageS(list:List<ShipmentInventoryBean> ,totalBean:ShipmentInventoryToTalBean,page: Int, count: String)
    }

    interface Presenter : BasePresenter<View> {

        fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String)
    }
}
