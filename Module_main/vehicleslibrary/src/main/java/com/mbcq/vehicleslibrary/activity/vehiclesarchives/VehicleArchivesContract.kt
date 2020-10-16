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
        fun deleteItemS(position: Int)
    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int, selWebidCode: String)
        fun deleteItem(json: String, position: Int)
        fun searchItem(id: String, commonStr: String)

    }
}
