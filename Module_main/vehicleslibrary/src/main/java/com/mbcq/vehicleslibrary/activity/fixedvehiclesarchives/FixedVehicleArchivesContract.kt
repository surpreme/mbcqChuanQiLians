package com.mbcq.vehicleslibrary.activity.fixedvehiclesarchives

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-14 17:32:12 修改车辆档案
 */

class FixedVehicleArchivesContract {

    interface View : BaseView {
        fun saveInfoS(result:String)
        fun getTransportModeS(result:String)

    }

    interface Presenter : BasePresenter<View> {
        fun saveInfo(jsonObject: JSONObject)
        fun getTransportMode()
    }
}
