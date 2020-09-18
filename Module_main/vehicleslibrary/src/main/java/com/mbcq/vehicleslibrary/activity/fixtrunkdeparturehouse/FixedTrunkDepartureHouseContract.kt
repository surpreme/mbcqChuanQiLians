package com.mbcq.vehicleslibrary.activity.fixtrunkdeparturehouse

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-09-18 11:33
 */

class FixedTrunkDepartureHouseContract {

    interface View : BaseView {

        fun modifyS()
    }

    interface Presenter : BasePresenter<View> {

        fun  modify(jsonObject: JSONObject)
    }
}
