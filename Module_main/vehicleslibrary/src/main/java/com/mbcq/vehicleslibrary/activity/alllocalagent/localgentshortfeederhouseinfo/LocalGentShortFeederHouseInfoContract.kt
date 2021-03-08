package com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouseinfo

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean

/**
 * @author: lzy
 * @time: 2021-03-02 15:23:03 本地代理详情页
 */

class LocalGentShortFeederHouseInfoContract {

    interface View : BaseView {
        fun getInventoryS(list: List<LocalGentShortFeederHouseBean>)
        fun getLoadingDataS(list: List<LocalGentShortFeederHouseBean>)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 库存
         */
        fun getInventory()
        fun getLoadingData(agentBillno: String)
    }
}
