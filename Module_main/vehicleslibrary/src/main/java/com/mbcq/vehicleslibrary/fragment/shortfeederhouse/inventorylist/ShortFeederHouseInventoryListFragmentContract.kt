package com.mbcq.vehicleslibrary.fragment.shortfeederhouse.inventorylist

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.fragment.shortfeederhouse.bean.ShortFeederHouseListBean

/**
 * @author: lzy
 * @time: 2020-09-15 11:01:40
 */

class ShortFeederHouseInventoryListFragmentContract {

    interface View : BaseView {
        fun getPageS(list:List<ShortFeederHouseListBean>)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int)

    }
}
