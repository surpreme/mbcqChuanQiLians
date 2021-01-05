package com.mbcq.vehicleslibrary.activity.shortfeederunloadingwarehousing

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView

/**
 * @author: lzy
 * @time: 2020-12-31 16:28:43 短驳卸车入库
 */

class ShortFeederUnloadingWarehousingContract {

    interface View : BaseView {
        fun getVehicleInfoS(list: List<ShortFeederUnloadingWarehousingBean>)
        fun getVehicleReceiptInfoS(list: List<ShortFeederUnloadingWarehousingBean>)

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
