package com.mbcq.vehicleslibrary.activity.vehiclesarchives

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-10-14 14:43:16 车辆档案
 */

class VehicleArchivesContract {

    interface View : BaseView {
        fun getPageS(list: List<VehicleArchivesBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int)

    }
}
