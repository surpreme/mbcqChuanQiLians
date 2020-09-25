package com.mbcq.vehicleslibrary.fragment.terminalagentbycar

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.fragment.localagentshortfeeder.LocalGentByCarBean

/**
 * @author: lzy
 * @time: 2018.08.25
 */

class TerminalAgentByCarContract {

    interface View : BaseView {
        fun getPageS(list: List<TerminalAgentByCarBean>)
        fun cancelS( position: Int)

    }

    interface Presenter : BasePresenter<View> {
        fun getPage(page: Int,selEwebidCode: String, startDate: String, endDate: String)
        fun cancel(s: TerminalAgentByCarBean, position: Int)

    }
}
