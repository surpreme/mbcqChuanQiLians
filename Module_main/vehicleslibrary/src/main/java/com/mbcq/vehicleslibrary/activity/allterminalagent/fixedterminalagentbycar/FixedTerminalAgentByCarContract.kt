package com.mbcq.vehicleslibrary.activity.allterminalagent.fixedterminalagentbycar

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.allterminalagent.TerminalAgentByCarHouseBean
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-25  11:30:15
 */

class FixedTerminalAgentByCarContract {

    interface View : BaseView {
        fun getInventoryS(list:List<TerminalAgentByCarHouseBean>)
        fun getLoadingDataS(list: List<TerminalAgentByCarHouseBean>)
        fun removeOrderS()
        fun removeOrderItemS(position: Int, item: TerminalAgentByCarHouseBean)
        fun addOrderS()
        fun addOrderItemS(position: Int, item: TerminalAgentByCarHouseBean)
    }

    interface Presenter : BasePresenter<View> {
        /**
         * 库存
         */
        fun getInventory()
        fun getLoadingData(agentBillno: String)
        fun removeOrder(removeOrderData: JSONObject)
        fun removeOrderItem(removeOrderData: JSONObject, position: Int, item: TerminalAgentByCarHouseBean)
        fun addOrder(removeOrderData: String)
        fun addOrderItem(removeOrderData: String,position: Int, item: TerminalAgentByCarHouseBean)
    }
}
