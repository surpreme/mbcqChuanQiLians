package com.mbcq.orderlibrary.activity.fixshipper

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2021-04-07 14:41:12 修改发货客户
 */

class FixShipperContract {

    interface View : BaseView {
        fun fixDataS(result: String)
    }

    interface Presenter : BasePresenter<View> {
        fun fixData(paramsObj: JSONObject)

    }
}
