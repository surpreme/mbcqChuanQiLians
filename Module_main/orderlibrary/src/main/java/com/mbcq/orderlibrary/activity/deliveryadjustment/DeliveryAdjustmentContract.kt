package com.mbcq.orderlibrary.activity.deliveryadjustment

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-16 14:27:44 放货调整
 */

class DeliveryAdjustmentContract {

    interface View : BaseView {
        fun getWillByInfoS(data: JSONObject)
        fun getWillByInfoNull()
        fun updateDataS()

    }

    interface Presenter : BasePresenter<View> {
        fun getWillByInfo(billno: String)
        fun updateData(jsonObject: JSONObject)

    }
}
