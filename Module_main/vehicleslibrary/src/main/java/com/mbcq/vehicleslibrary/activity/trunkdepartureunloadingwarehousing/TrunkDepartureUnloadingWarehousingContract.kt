package com.mbcq.vehicleslibrary.activity.trunkdepartureunloadingwarehousing

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-12-31 16:26:03 干线卸车入库
 */

class TrunkDepartureUnloadingWarehousingContract {

    interface View : BaseView {
        fun getVehicleInfoS(list: List<TrunkDepartureUnloadingWarehousingBean>)
        fun getVehicleReceiptInfoS(list: List<TrunkDepartureUnloadingWarehousingBean>)

        fun UnloadingWarehousingS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        fun getVehicleInfo(inoneVehicleFlag: String)
        fun getVehicleReceiptInfo(inoneVehicleFlag: String)

        /**
         * 卸车入库
         */
        fun UnloadingWarehousing(commonStr: String, inoneVehicleFlag: String)
    }
}
