package com.mbcq.orderlibrary.activity.fixreceiver

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-04-07 17:46:43 修改收货客户
 */

class FixReceiverContract {

    interface View : BaseView {
        fun fixDataS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun fixData(paramsObj: JSONObject)
    }
}
