package com.mbcq.vehicleslibrary.activity.fixeddeparturetrunkconfiguration

import com.mbcq.baselibrary.ui.mvp.BasePresenter
import com.mbcq.baselibrary.ui.mvp.BaseView
import com.mbcq.vehicleslibrary.activity.fixedscandeparturetrunkconfiguration.FixedScanDepartureTrunkConfigurationBean

/**
 * @author: lzy
 * @time: 2020-12-21 17:26:04  修改干线配置信息
 */

class FixedDepartureTrunkConfigurationContract {

    interface View : BaseView {
        fun getVehicleS(result: String)
        fun getCarInfoS(result: FixedScanDepartureTrunkConfigurationBean)
        fun geSelectVehicleS(result: String, vehicleNo: String, chaufferMb: String)
        fun saveInfoS(result: String)

    }

    interface Presenter : BasePresenter<View> {
        fun getVehicles()
        fun getCarInfo(inoneVehicleFlag: String)
        /**
         * 由于车辆信息只是展示 后台不返回 这里判断车牌号和司机手机号 如果还有雷同找后台
         */
        fun geSelectVehicles(vehicleNo: String, chaufferMb: String)
        fun saveInfo(postJson: String)

    }
}
