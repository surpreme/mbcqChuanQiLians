package com.mbcq.vehicleslibrary.activity.addvehiclesarchives

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import org.json.JSONObject

/**
 * @author: lzy
 * @time: 2020-10-14 17:20:45 添加车辆档案
 */

class AddVehicleArchivesContract {

    interface View : BaseView {
        fun saveInfoS(result:String)
        fun getTransportModeS(result:String)

    }

    interface Presenter : BasePresenter<View> {
        fun saveInfo(jsonObject:JSONObject)
        fun getTransportMode()
    }
}
