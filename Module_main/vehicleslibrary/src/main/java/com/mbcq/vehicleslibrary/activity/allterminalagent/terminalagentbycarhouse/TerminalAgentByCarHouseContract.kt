package com.mbcq.vehicleslibrary.activity.allterminalagent.terminalagentbycarhouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.alllocalagent.localgentshortfeederhouse.LocalGentShortFeederHouseBean
import com.mbcq.vehicleslibrary.activity.allterminalagent.TerminalAgentByCarHouseBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-25 13:33:00
 */

class TerminalAgentByCarHouseContract {

    interface View : BaseView {
        fun getInventoryS(list:List<TerminalAgentByCarHouseBean>)
        fun  completeVehicleS()

    }

    interface Presenter : BasePresenter<View> {
        /**
         * 库存
         */
        fun getInventory()
        /**
         * 完成本车 车辆
         */
        fun completeVehicle(s: JSONObject)
    }
}
