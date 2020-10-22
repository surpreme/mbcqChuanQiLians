package com.mbcq.orderlibrary.activity.waybilldetails

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-20 10:13:00
 */

class WaybillDetailsContract {

    interface View : BaseView {
        fun getWaybillDetailS(data: JSONObject)
        fun getWaybillDetailNull()

    }

    interface Presenter : BasePresenter<View> {

        fun getWaybillDetail(billNo: String)
    }
}
