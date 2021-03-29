package com.mbcq.vehicleslibrary.activity.homedelivery

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-01-14 15:44:03 上门提货
 */

class HomeDeliveryContract {

    interface View : BaseView {

        fun getPageS(list: List<HomeDeliveryBean>)
        fun onDeleteS(position: Int)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int, selWebidCode: String, startDate: String, endDate: String)
        fun onDelete(json: String, position: Int)

    }
}
