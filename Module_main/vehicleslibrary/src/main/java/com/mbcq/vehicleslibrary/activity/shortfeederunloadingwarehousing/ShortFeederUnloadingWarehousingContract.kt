package com.mbcq.vehicleslibrary.activity.shortfeederunloadingwarehousing

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-12-31 16:28:43 短驳卸车入库
 */

class ShortFeederUnloadingWarehousingContract {

    interface View : BaseView {

    }

    interface Presenter : BasePresenter<View> {
        fun getVehicleInfo(inoneVehicleFlag: String)

    }
}
